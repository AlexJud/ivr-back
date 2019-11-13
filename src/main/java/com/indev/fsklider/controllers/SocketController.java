package com.indev.fsklider.controllers;

import com.indev.fsklider.beans.socket.SocketRequest;
import com.indev.fsklider.beans.socket.UserMessage;
import lombok.extern.log4j.Log4j;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

//@Controller
//@Log4j
public class SocketController {
//    @Autowired
//    private SimpMessagingTemplate template;
//
//    @MessageMapping("/echo")
//    @SendTo("/ivr/dialog")
//    public UserMessage greeting(SocketRequest message) {
//        log.info("Received message from client " + message);
//        return new UserMessage();
//    }
}
