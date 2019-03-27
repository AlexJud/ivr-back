package com.indev.fsklider.graph.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Relation {
    private String id;
    private ArrayList<String> match;
    private HashMap<String, ArrayList<String>> matchGroup = new HashMap<>();
    private String test;
    private Integer repeatMax;

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

    public Integer getRepeatMax() {
        return repeatMax;
    }

    public void setRepeatMax(Integer repeatMax) {
        this.repeatMax = repeatMax;
    }

    public HashMap<String, ArrayList<String>> getMatchGroup() {
        return matchGroup;
    }

    public void setMatchGroup(HashMap<String, ArrayList<String>> matchGroup) {
        this.matchGroup = matchGroup;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "id='" + id + '\'' +
                ", match=" + match +
                ", repeatMax=" + repeatMax +
                '}';
    }
}
