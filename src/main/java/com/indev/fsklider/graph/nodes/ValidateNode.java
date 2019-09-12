package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.nodes.properties.ValidateProps;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter
public class ValidateNode extends Node {
    private ValidateProps props;
    private Relation edgeIfEmpty;


    @Override
    public String run() {
        return calculateNext();
    }

    private String calculateNext() {
        System.out.println(this);
        HashMap<String, String> context = getContext().getContextMap();
        if (context.containsKey(props.getVarName())) {
            return getEdgeList().get(0).getId();
        } else  {
            return getEdgeIfEmpty().getId();
        }
    }

    @Override
    public String toString() {
        return "ValidateNode{" +
                "props=" + props +
                ", edgeIfEmpty=" + edgeIfEmpty +
                ", edgeList=" + getEdgeList() +
                '}';
    }
}
