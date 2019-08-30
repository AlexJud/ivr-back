package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.nodes.properties.SystemProps;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter @Setter
public class SystemNode extends Node {

    private SystemProps props;
    private ArrayList<Relation> edgeList;

    @Override
    public String run() {
        String sysVar = getContext().getContextMap().get(props.getSystemVar());
        getContext().getContextMap().put(props.getVarName(), sysVar);
        return edgeList.get(0).getId();
    }
}
