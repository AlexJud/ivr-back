package com.indev.fsklider.controllers;

import com.indev.fsklider.beans.socket.SocketRequest;
import com.indev.fsklider.beans.socket.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/echo")
    @SendTo("/ivr/dialog")
    public UserMessage greeting(SocketRequest message) {
        System.out.println("Received message from client " + message);
        return new UserMessage();
    }
}
