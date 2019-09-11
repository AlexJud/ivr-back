package com.indev.fsklider.graph.nodes;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ClassifierNode extends Node {

    @Override
    public String run() {
        HashMap<String, String> contextMap = getContext().getContextMap();
        @NotNull
        String asrResult = getContext().getRecogResult();
//        System.out.println(asrResult);
        String nextNodeId = null;
        for (Relation relation : getEdgeList()) {
            if (relation.getMatch() == null) {
                nextNodeId = relation.getId();
            } else {
                for (String match : relation.getMatch()) {
                    match = match.toLowerCase();
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
    @Override
    public String toString() {
        return "ClassifierNode{" +
                "id=" + getId() +
                " edgeList=" + getEdgeList() +
                '}';
    }
}
