package org.handnotes.wss;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class WsSessionManager {

    final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(String userId, WebSocketSession session){
        sessions.put(userId, session);
    }

    public void removeSession(String userId){
        sessions.remove(userId);
    }

    public WebSocketSession getSession(String userId){
        return sessions.get(userId);
    }

    public Map<String, WebSocketSession> getAllSessions() {
        return new ConcurrentHashMap<>(sessions);
    }

}
