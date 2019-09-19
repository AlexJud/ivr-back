package com.indev.fsklider.services;

import com.indev.fsklider.beans.socket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void fireGreeting(SocketMessage message) {
        System.out.println("Sending message " + message);
        this.template.convertAndSend("/ivr/dialog", message);
    }

    public void sendSystemMessage(String text) {
        SystemMessage message = new SystemMessage();
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        message.setDate(format.format(curDate));
        message.setType(MessageType.SYSTEM);
        message.setLevel("info");
        message.setMessage(text);
        fireGreeting(message);
    }

    public void sendHighlightMessage(String text) {
        SystemMessage message = new SystemMessage();
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        message.setDate(format.format(curDate));
        message.setType(MessageType.HIGHLIGHT);
        message.setLevel("highlight");
        message.setMessage(text);
        fireGreeting(message);
    }

    public void sendServerMessage(String text) {
        ServerMessage message = new ServerMessage();
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        message.setDate(format.format(curDate));
        message.setType(MessageType.SERVER);
        message.setName("Сервер");
        message.setMessage(text);
        fireGreeting(message);
    }

    public void sendUserMessage(String user, String text) {
        UserMessage message = new UserMessage();
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        message.setDate(format.format(curDate));
        message.setType(MessageType.USER);
        message.setName(user);
        message.setMessage(text);
        fireGreeting(message);
    }


}
