package org.dosilock.socket;

import org.dosilock.socket.redis.NamespaceRedis;
import org.dosilock.socket.redis.NamespaceRedisRepository;
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

	@PostConstruct
	public void init() {
		server.addConnectListener(onConnected());
		server.addDisconnectListener(onDisconnected());

		namespaceRedisRepository.findAll().forEach(e -> {
			System.out.println(e.getName());
			createNamespace(e.getName());
		});
	}

	public void addNamespace(String name) {
		if (server.getAllNamespaces().stream().filter(e -> e.getName().equals("/" + name)).count() > 0) {
			System.out.println("이미 존재 합니다.");
			return;
		}

		createNamespace(name);
		namespaceRedisRepository.save(NamespaceRedis.builder().name(name).build());
	}

	private DataListener<Message> onMemberInfo() {
		return (client, data, ackSender) -> {
			System.out.println("MemberInfo: " + data.getPayload());

			sendAllMessage(MessageType.FOCUS_MESSAGE, client, data.getPayload());
		};
	}

	public DataListener<Message> onFocusMessage() {
		return (client, data, ackSender) -> {
			System.out.println("FocusMessage: " + data.getPayload());

			if (data.getPayload().equals("start")) { // 집중시간 시작

			} else { // 집중시간 종료

			}

			sendMessage(MessageType.FOCUS_MESSAGE, client, data.getPayload());
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
		namespace.addEventListener(MessageType.MEMBERS_INFO.name(), Message.class, onMemberInfo());
	}

	private void sendAllMessage(MessageType type, SocketIOClient senderClient, String message) {
		senderClient.getNamespace().getAllClients().parallelStream().forEach(c -> {
			c.sendEvent(type.name(), new Message(message));
		});
	}

	private void sendMessage(MessageType type, SocketIOClient senderClient, String message) {
		senderClient.sendEvent(type.name(), new Message(message));
	}
}
