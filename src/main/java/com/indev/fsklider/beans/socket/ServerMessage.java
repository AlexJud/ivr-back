package com.indev.fsklider.beans.socket;

import lombok.Data;

@Data
public class ServerMessage implements SocketMessage {
    private MessageType type;
    private String date;
    private String message;
    private String name;

}
