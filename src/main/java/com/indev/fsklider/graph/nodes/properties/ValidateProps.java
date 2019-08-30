package com.indev.fsklider.graph.nodes.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class ValidateProps {
    private ArrayList<ValidatePropsVarListItem> varList;
    private String edgeIfSuccess;

    @Override
    public String toString() {
        return "ValidateProps{" +
                "varList=" + varList +
                ", edgeIfSuccess='" + edgeIfSuccess + '\'' +
                '}';
    }
}
