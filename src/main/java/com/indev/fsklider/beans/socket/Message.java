package com.indev.fsklider.beans.socket;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
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
