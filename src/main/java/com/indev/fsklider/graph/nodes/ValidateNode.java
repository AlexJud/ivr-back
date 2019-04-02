package com.indev.fsklider.graph.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class ValidateNode extends Node {
    @JsonProperty("props")
    private ValidateProps props;

    @Override
    public String run() {
        for (ValidatePropsVarListItem item : props.getVarList()) {
            HashMap<String, String> context = getContext().getContextMap();
            if (!context.containsKey(item.getVarName())) {
                return item.getEdgeIfEmpty();
            }
        }
        return props.getEdgeIfSuccess();
    }
}
