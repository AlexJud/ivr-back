package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.properties.ValidateProps;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.util.HashMap;

@Getter @Setter
public class ValidateNode extends Node {
    private ValidateProps props;
    private Relation edgeIfEmpty;
    private static final Logger log = Logger.getLogger(Incoming.class);

    @Override
    public String run() {
        log.info("Выполняется " + this.getClass().getSimpleName() + ": " + getId());
        return calculateNext();
    }

    private String calculateNext() {
        HashMap<String, String> context = getContext().getContextMap();
        String nextId;
        if (context.containsKey(props.getVarName())) {
            nextId = getEdgeList().get(0).getId();
            log.info("Выбран переход по ветке EdgeList в " + nextId + ", так как Контекст содержит переменную " + props.getVarName());
            return nextId;
        } else  {
            nextId = getEdgeIfEmpty().getId();
            log.info("Выбран переход по ветке EdgeIfEmpty в " + nextId + ", так как Контекст НЕ содержит переменную " + props.getVarName());
            return nextId;
        }
    }

    @Override
    public String toString() {
        return "ValidateNode{" +
                "id=" + getId() +
                " props=" + props +
                ", edgeIfEmpty=" + edgeIfEmpty +
                ", edgeList=" + getEdgeList() +
                '}';
    }
}
