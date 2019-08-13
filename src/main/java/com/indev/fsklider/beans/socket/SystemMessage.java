package com.indev.fsklider.beans.socket;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class SystemMessage implements SocketMessage {
    private String level;
    private MessageType type;
    private String date;
    private String message;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void main(String[] args) {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String DateToStr = format.format(curDate);
        System.out.println(DateToStr);
    }
}
