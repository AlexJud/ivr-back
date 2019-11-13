package com.indev.fsklider.beans.socket;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SystemMessage implements SocketMessage {
    private String level;
    private MessageType type;
    private String date;
    private String message;
//
//    public static void main(String[] args) {
//        Date curDate = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//        String DateToStr = format.format(curDate);
//        System.out.println(DateToStr);
//    }
}
