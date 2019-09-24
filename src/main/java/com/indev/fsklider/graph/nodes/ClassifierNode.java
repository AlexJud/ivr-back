package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.agiscripts.Incoming;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class ClassifierNode extends Node {

    private static final Logger log = Logger.getLogger(Incoming.class);

    @Override
    public String run() {
        log.info("Выполняется " + this.getClass().getSimpleName() + ": " + getId());
        HashMap<String, String> contextMap = getContext().getContextMap();
        String asrResult = getContext().getRecogResult();

        String nextNodeId = null;
        for (Relation relation : getEdgeList()) {
            if (relation.getMatch() == null || relation.getMatch().size() == 0) {
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
        log.info("Node: " + getId() + " - завершил выполнение");
        log.info("Выбрана ветка: " + nextNodeId + ". Так как фраза: " + asrResult + "\nСодержит слово: " + getContext().getContextMap().get("reason"));
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
