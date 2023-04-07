package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.pojo.Seats;

import com.ezticket.web.activity.service.SeatsService;
import com.google.gson.Gson;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@ServerEndpoint("/SelectSeatsTogether/Sync")
public class SelectSeatsTogether {

    @Autowired
    private SeatsService seatsService;



    private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session userSession) throws IOException {
        connectedSessions.add(userSession);
        String text = String.format("Session ID = %s, connected", userSession.getId());
        System.out.println(text);
    }

//    重點是這
    @OnMessage
    public void onMessage(Session userSession, String message) {
        Gson gson = new Gson();
        Seats seat = gson.fromJson(message, Seats.class);

        for (Session session : connectedSessions) {
            if (session.isOpen() && (session != userSession))
                session.getAsyncRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        connectedSessions.remove(userSession);
        String text = String.format("session ID = %s, disconnected; close code = %d; reason phrase = %s",
                userSession.getId(), reason.getCloseCode().getCode(), reason.getReasonPhrase());
        System.out.println(text);
    }

    @OnError
    public void onError(Session userSession, Throwable e) {
        System.out.println("Error: " + e.toString());
    }

}
