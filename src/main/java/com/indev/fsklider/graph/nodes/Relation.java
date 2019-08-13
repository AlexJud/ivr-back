package com.indev.fsklider.graph.nodes;

import java.util.ArrayList;

public class Relation {
    private String id;
    private ArrayList<String> match;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getMatch() {
        return match;
    }

    public void setMatch(ArrayList<String> match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "id='" + id + '\'' +
                ", match=" + match +
                '}';
    }
}
