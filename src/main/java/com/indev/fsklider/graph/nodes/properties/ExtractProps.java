package com.indev.fsklider.graph.nodes.properties;

import java.util.ArrayList;

public class ExtractProps {
    private String varName;
    private String rawVarName;
    private ArrayList<String> match;
    private String matchFile;

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public ArrayList<String> getMatch() {
        return match;
    }

    public String getRawVarName() {
        return rawVarName;
    }

    public void setRawVarName(String rawVarName) {
        this.rawVarName = rawVarName;
    }

    public void setMatch(ArrayList<String> match) {
        this.match = match;
    }

    public String getMatchFile() {
        return matchFile;
    }

    public void setMatchFile(String matchFile) {
        this.matchFile = matchFile;
    }

    @Override
    public String toString() {
        return "ExtractProps{" +
                "varName='" + varName + '\'' +
                ", match=" + match +
                '}';
    }
}
