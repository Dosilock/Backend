package org.dosilock.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

@Component
public class SocketIOServerManager {

	private final SocketIOServer server;

	@Autowired
	public SocketIOServerManager(SocketIOServer server) {
		this.server = server;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startServer() {
		server.start();
		System.out.println("Socket.IO server started");
	}

	@EventListener(org.springframework.context.event.ContextClosedEvent.class)
	public void stopServer() {
		server.stop();
		System.out.println("Socket.IO server stopped");
	}
}
