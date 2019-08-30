package com.indev.fsklider.graph.nodes.properties;

import com.indev.fsklider.graph.nodes.Relation;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter @Setter
public class ValidatePropsVarListItem {
    private String varName;
    private String rawVarName;
    private Integer repeatCount = 0;
    private Integer repeatMax;
    private ArrayList<Relation> edgeIfEmpty;

    public void incrementRepeatCount() {
        repeatCount++;
    }

    @Override
    public String toString() {
        return "ValidatePropsVarListItem{" +
                "varName='" + varName + '\'' +
                ", rawVarName='" + rawVarName + '\'' +
                ", edgeIfEmpty='" + edgeIfEmpty + '\'' +
                '}';
    }
}
