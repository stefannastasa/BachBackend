package org.handnotes.wss;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public class WebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Ws session received");
        String username = extractUserIdFromSession(session);
        if(username != null)
            userSessions.put(username, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userSessions.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming WebSocket messages if necessary
    }

    public void sendMessage(String userId, String message) throws Exception {
        System.out.println("Sending prediction to client...");
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

    public WebSocketSession getSession(String userId){
        return userSessions.get(userId);
    }

    private String extractUserIdFromSession(WebSocketSession session) {
        return (String) session.getAttributes().get("username");
    }
}
