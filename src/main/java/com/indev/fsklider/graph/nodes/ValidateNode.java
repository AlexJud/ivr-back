package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.nodes.properties.ValidateProps;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter
public class ValidateNode extends Node {
    private ValidateProps props;

    @Override
    public String run() {
        return calculateNext();
    }

    private String calculateNext() {
        HashMap<String, String> context = getContext().getContextMap();
        if (context.containsKey(props.getVarName())) {
            return getEdgeList().get(0).getId();
        } else  {
            return props.getEdgeIfEmpty().getId();
        }
    }

    @Override
    public String toString() {
        return "ValidateNode{" +
                "id=" + getId() +
                " props=" + props +
                '}';
    }
}
