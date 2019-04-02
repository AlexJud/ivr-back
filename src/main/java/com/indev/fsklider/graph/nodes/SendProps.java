package com.indev.fsklider.graph.nodes;

import java.util.ArrayList;

public class SendProps {
    private String dest;
    private ArrayList<String> varList;

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public ArrayList<String> getVarList() {
        return varList;
    }

    public void setVarList(ArrayList<String> varList) {
        this.varList = varList;
    }
}
