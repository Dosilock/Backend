package org.dosilock.socket;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatEventHandler {

	private final SocketService socketService;
	private final SocketIOServer server;

	@PostConstruct
	public void init() {
		server.addConnectListener(onConnected());
		server.addDisconnectListener(onDisconnected());
		server.addEventListener("send_message", Message.class, onChatReceived());
	}

	private ConnectListener onConnected() {
		return client -> {
			System.out.println("Client connected: " + client.getSessionId());
			String room = client.getHandshakeData().getSingleUrlParam("room");
			client.joinRoom(room);
		};
	}

	private DisconnectListener onDisconnected() {
		return client -> {
			System.out.println("Client disconnected: " + client.getSessionId());
		};
	}

	private DataListener<Message> onChatReceived() {
		return (client, data, ackSender) -> {
			System.out.println("Received chat: " + data.getMessage());
			socketService.sendMessage(data.getRoom(), "get_message", client, data.getMessage());
		};
	}
}
