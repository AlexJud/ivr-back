package com.indev.fsklider.graph.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.indev.fsklider.graph.nodes.properties.ValidateProps;
import com.indev.fsklider.graph.nodes.properties.ValidatePropsVarListItem;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter
public class ValidateNode extends Node {
    @JsonProperty("props")
    private ValidateProps props;

    @Override
    public String run() {
        for (ValidatePropsVarListItem item : props.getVarList()) {
            HashMap<String, String> context = getContext().getContextMap();
            //Если число повторов достигло максимума, создадим переменную
            //и запишем туда пустоту чтобы Валидатор больше к ней не обращался.
            if (item.getRepeatCount().equals(item.getRepeatMax())) {
                insert(item.getVarName(), "");
                item.setRepeatCount(0);
            } else if ( context.get(item.getVarName()) == null ) {
                return calculateNext(item);
            }
        }
        return props.getEdgeIfSuccess();
    }

    private String calculateNext(ValidatePropsVarListItem item) {
        String nextId = item.getEdgeIfEmpty().get(item.getRepeatCount()).getId();
        item.incrementRepeatCount();
        return nextId;
    }

    @Override
    public String toString() {
        return "ValidateNode{" +
                "id=" + getId() +
                " props=" + props +
                '}';
    }
}
