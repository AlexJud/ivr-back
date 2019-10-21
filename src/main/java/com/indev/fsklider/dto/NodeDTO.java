package com.indev.fsklider.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NodeDTO {
    private String id;
    private String type;
    private PropertiesNode props;
    private ArrayList<EdgeList> edgeList;

}
