package com.indev.fsklider.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NodeDTO {
    private String id;
    private String type;
    private PropertiesNode props;
    private ArrayList<EdgeList> edgeList;

//    public NodeDTO(String id) {
//        this.id = id;
//        PropertiesNode props = new PropertiesNode();
//
//    }

}
