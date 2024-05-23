package org.dosilock.socket;

import java.util.Base64;
import java.util.List;

import org.dosilock.clazz.entity.Clazz;
import org.dosilock.clazz.entity.FocusTime;
import org.dosilock.clazz.repository.ClazzRepository;
import org.dosilock.clazz.repository.FocusTimeRepository;
import org.dosilock.clazz.response.FocusTimeResponse;
import org.dosilock.exception.ErrorMessage;
import org.dosilock.exception.ErrorResponseDto;
import org.dosilock.exception.UserErrorException;
import org.dosilock.member.entity.Member;
import org.dosilock.member.repository.MemberRepository;
import org.dosilock.socket.redis.NamespaceRedis;
import org.dosilock.socket.redis.NamespaceRedisRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocketService {

	private final SocketIOServer server;
	private final NamespaceRedisRepository namespaceRedisRepository;

	private final FocusTimeRepository focusTimeRepository;
	private final ClazzRepository clazzRepository;
	private final MemberRepository memberRepository;

	private final SessionRepository<? extends Session> sessionRepository;

	@PostConstruct
	public void init() {
		server.addConnectListener(onConnected());
		server.addDisconnectListener(onDisconnected());

		namespaceRedisRepository.findAll().forEach(e -> {
			createNamespace(e.getName());
		});
	}

	public void addNamespace(String name) {
		if (server.getAllNamespaces().stream().filter(e -> e.getName().equals("/" + name)).count() > 0) {
			throw new UserErrorException(new ErrorResponseDto(ErrorMessage.ALREADY_NAMESPACE));
		}

		createNamespace(name);
		namespaceRedisRepository.save(NamespaceRedis.builder().name(name).build());
	}

	private DataListener<FocusTimeResponse> onMemberInfo() {
		return (client, data, ackSender) -> {
			Clazz clazz = clazzRepository.findByClazzLink(client.getNamespace().getName().replaceAll("/", ""));
			List<FocusTimeResponse> focusTimeList = focusTimeRepository.findByClazz(clazz)
				.stream()
				.map(FocusTimeResponse::new)
				.toList();
			sendAllMessage(MessageType.MEMBERS_INFO, client, focusTimeList);
		};
	}

	public DataListener<Message> onFocusMessage() {
		return (client, data, ackSender) -> {
			System.out.println("FocusMessage: " + data.getPayload());
			Clazz clazz = clazzRepository.findByClazzLink(client.getNamespace().getName().replaceAll("/", ""));

			Member member = memberRepository.findByEmail(getEmail(client))
				.orElseThrow(() -> new UserErrorException(new ErrorResponseDto(ErrorMessage.USER_NOT_FOUND)));

			if (!data.getPayload().equals("start") && !data.getPayload().equals("stop")) {
				throw new UserErrorException(new ErrorResponseDto(ErrorMessage.NOT_FOCUS_MESSAGE));
			}

			focusTimeRepository.save(FocusTime.builder()
				.clazz(clazz)
				.member(member)
				.timestamp(System.currentTimeMillis())
				.focusType(data.getPayload().toString())
				.build());

			sendMessage(MessageType.FOCUS_MESSAGE, client, data.getPayload().toString());
		};
	}

	private ConnectListener onConnected() {
		return client -> {
			System.out.println("Client connected: " + client.getSessionId());
		};
	}

	private DisconnectListener onDisconnected() {
		return client -> {
			System.out.println("Client disconnected: " + client.getSessionId());
		};
	}

	private void createNamespace(String name) {
		SocketIONamespace namespace = server.addNamespace("/" + name);
		namespace.addEventListener(MessageType.FOCUS_MESSAGE.name(), Message.class, onFocusMessage());
		namespace.addEventListener(MessageType.MEMBERS_INFO.name(), FocusTimeResponse.class, onMemberInfo());
	}

	private String getEmail(SocketIOClient client) {
		String sessionId = new String(
			Base64.getDecoder().decode(client.getHandshakeData().getUrlParams().get("uid").get(0)));
		Session session = sessionRepository.findById(sessionId);

		SecurityContext securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");
		return securityContext.getAuthentication().getName();
	}

	private void sendAllMessage(MessageType type, SocketIOClient senderClient, Object message) {
		senderClient.getNamespace().getAllClients().parallelStream().forEach(c -> {
			c.sendEvent(type.name(), new Message(message));
		});
	}

	private void sendMessage(MessageType type, SocketIOClient senderClient, Object message) {
		senderClient.sendEvent(type.name(), new Message(message));
	}
}
