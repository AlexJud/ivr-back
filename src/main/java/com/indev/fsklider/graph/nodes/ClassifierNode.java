package com.indev.fsklider.graph.nodes;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ClassifierNode extends Node {

    @Override
    public String run() {
        HashMap<String, String> contextMap = getContext().getContextMap();
        String asrResult = getContext().getRecogResult();
        System.out.println(asrResult);
        String nextNodeId = null;
        for (Relation relation : getEdgeList()) {
            if (relation.getMatch() == null) {
                nextNodeId = relation.getId();
            } else {
                for (String match : relation.getMatch()) {
                    if (asrResult.contains(match)) {
                        contextMap.put("reason", match);
                        nextNodeId = relation.getId();
                        return nextNodeId;
                    }
                }
            }
        }
        return nextNodeId;
    }
}
