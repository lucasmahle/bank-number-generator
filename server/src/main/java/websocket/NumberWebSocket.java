package websocket;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

import application.Application;

@WebSocket
public class NumberWebSocket {
	public static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

	@OnWebSocketConnect
	public void connected(Session session) {
		System.out.println("WS Connected");
		sessions.add(session);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason) {
		System.out.println("WS Closed");
		sessions.remove(session);
	}

	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		System.out.println("Got: " + message);  
		session.getRemote().sendString(message); 
	}
	
	public static void broadcast(String message) throws IOException {
		for (Session session : sessions) {
			session.getRemote().sendString(message);
		}
	}
	
}
