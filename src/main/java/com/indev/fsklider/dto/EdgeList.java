package com.indev.fsklider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EdgeList {
    private String id;
    private ArrayList<String> match;
    private String name;
}
