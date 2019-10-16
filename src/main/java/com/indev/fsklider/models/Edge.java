package com.indev.fsklider.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Edge {

    private String id;
    private String name;
    private String sourceId;
    private String targetId;
    private String matchWord;
    private ArrayList<String> keyWords;


    public Edge(String sourceId, String targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
    }
}
