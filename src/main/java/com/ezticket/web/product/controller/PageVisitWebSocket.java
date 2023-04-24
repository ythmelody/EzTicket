package com.ezticket.web.product.controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@ServerEndpoint("/visitWS/{productno}")
public class PageVisitWebSocket {
    
    //拿來存放各頁面的WS session
    public static Map<Integer, Set<Session>>  pages = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void onOpen(@PathParam("productno")Integer productno,Session session) throws IOException{
        //從上面的全域變數找到單一頁面的連線sessions
        Set<Session> sessions = pages.getOrDefault(productno,Collections.synchronizedSet(new HashSet<>()));
        //將傳入的session加進去sessions
        sessions.add(session);
        //更新pages資料
        pages.put(productno,sessions);
        broadcast(productno,"這個頁面目前有"+sessions.size()+"人瀏覽");
    }
    @OnClose
    public void onClose(@PathParam("productno")Integer productno,Session session) throws IOException{
        Set<Session> sessions =pages.getOrDefault(productno,Collections.synchronizedSet(new HashSet<>()));
        sessions.remove(session);
        pages.put(productno,sessions);
        broadcast(productno,"這個頁面目前有"+sessions.size()+"人瀏覽");

    }

    private void broadcast(Integer productno , String message) throws IOException{
        Set<Session> sessions =pages.getOrDefault(productno,Collections.synchronizedSet(new HashSet<>()));
        for(Session session :sessions){
                session.getAsyncRemote().sendText(message);

        }
    }
}
