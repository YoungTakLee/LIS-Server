package com.green.service.impl;

import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Service
@ServerEndpoint(value = "/lis")
public class LisWebSocketService {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());
    private static Map<String, Session> clientsMap = Collections.synchronizedMap(new HashMap<>());

    public void sendMessage() {
        clients.forEach(client -> {
            System.out.println("client = " + client);
            try {
                synchronized (client) {
                    client.getBasicRemote().sendText("refresh");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @OnOpen
    public void onOpen(Session s) {
        if (!clients.contains(s)) {
            clients.add(s);
            System.out.println("session open : " + s.getId());
        } else {
            System.out.println("이미 연결된 session 임!!!");
        }
    }

    @OnClose
    public void onClose(Session s) {
        System.out.println("session close : " + s.getId());
        clients.remove(s);
        Optional<Map.Entry<String, Session>> first = clientsMap.entrySet().stream().filter(entry -> entry.getValue().equals(s)).findFirst();
        if(first.isPresent()) {
            Map.Entry<String, Session> stringSessionEntry = first.get();
            clientsMap.remove(stringSessionEntry.getKey());
        }
    }
}
