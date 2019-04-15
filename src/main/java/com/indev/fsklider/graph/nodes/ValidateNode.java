package com.indev.fsklider.graph.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.indev.fsklider.graph.nodes.properties.ValidateProps;
import com.indev.fsklider.graph.nodes.properties.ValidatePropsVarListItem;

import java.util.HashMap;

public class ValidateNode extends Node {
    @JsonProperty("props")
    private ValidateProps props;

    public ValidateProps getProps() {
        return props;
    }

    public void setProps(ValidateProps props) {
        this.props = props;
    }

    @Override
    public String run() {
        for (ValidatePropsVarListItem item : props.getVarList()) {
            HashMap<String, String> context = getContext().getContextMap();
            if (!(context.containsKey(item.getVarName())) && !(context.containsKey(item.getRawVarName())) ) {
                return item.getEdgeIfEmpty();
            }
        }
        return props.getEdgeIfSuccess();
    }
}
