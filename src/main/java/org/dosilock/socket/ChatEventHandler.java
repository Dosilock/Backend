package org.dosilock.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import jakarta.annotation.PostConstruct;

@Component
public class ChatEventHandler {

	private final SocketIOServer server;

	@Autowired
	public ChatEventHandler(SocketIOServer server) {
		this.server = server;
	}

	@PostConstruct
	public void init() {
		server.addConnectListener(onConnected());
		server.addDisconnectListener(onDisconnected());
		server.addEventListener("chat", String.class, onChatReceived());
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

	private DataListener<String> onChatReceived() {
		return (client, data, ackSender) -> {
			System.out.println("Received chat: " + data);
			// 모든 클라이언트에게 메시지 전송
			server.getBroadcastOperations().sendEvent("chat", data);
		};
	}
}
