package com.indev.fsklider.graph.nodes.properties;

import com.indev.fsklider.graph.nodes.Relation;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValidateProps {
    private String varName;
    private Relation edgeIfEmpty;

    @Override
    public String toString() {
        return "ValidateProps{" +
                "varName='" + varName + '\'' +
                ", edgeIfEmpty=" + edgeIfEmpty +
                '}';
    }
}
