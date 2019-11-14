package com.indev.fsklider.services;

import com.indev.fsklider.models.Message;
import com.indev.fsklider.models.enums.MessageType;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Log4j
@Component
public class SocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendMessage(String text, MessageType type, String channel) {
        Message mes = new Message();
                mes.setType(type);
        mes.setText(text);
        mes.setText(text);
        mes.setSentTime(LocalTime.now());
        mes.setDestination(channel);

        log.info("message " + mes + "  sent to " + channel);
        this.template.convertAndSend("/ivr/dialog", mes);
    }

}
