package com.indev.fsklider.graph.nodes.properties;

public class ActionProps {
    private String synthText;
    private String grammar;
    private String options;

    public String getSynthText() {
        return synthText;
    }

    public void setSynthText(String synthText) {
        this.synthText = synthText;
    }

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "ActionProps{" +
                "synthText='" + synthText + '\'' +
                ", grammar='" + grammar + '\'' +
                ", options='" + options + '\'' +
                '}';
    }
}
