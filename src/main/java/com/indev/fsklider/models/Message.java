package com.indev.fsklider.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.indev.fsklider.models.enums.MessageType;
import lombok.Data;

import java.time.LocalTime;

@Data
public class Message {
    private long id;
    private String destination;
    private String source;
    private String text;
    private MessageType type;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime sentTime;
    private String title;

}
