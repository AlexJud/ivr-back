package com.indev.fsklider.graph.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.indev.fsklider.graph.nodes.properties.ActionProps;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.Stack;
@Getter @Setter
public class ActionNode extends Node {

    @JsonProperty("props")
    private ActionProps props;

    private String calculateNext() {
        return getEdgeList().get(0).getId();
    }

    @Override
    public String run() {
        Stack<Command> commandList = getContext().getCommands();
        Command command = new Command();
        command.setApp("SynthAndRecog");
        String text = replaceVar(props.getSynthText());
        command.setOption(text + ", " +  props.getGrammar() + ", " + props.getOptions());
        getContext().getEvent().setSystemText(Utils.removeBackslash(text));
        commandList.push(command);
        getContext().setCommands(commandList);
        return calculateNext();
    }

    public String getMessage() {
        return getContext().getEvent().getSystemText();
    }

    @Override
    public String toString() {
        return "ActionNode{" +
                "id='" + getId() + '\'' +
                ", props=" + props +
                ", edgeList=" + getEdgeList() +
                '}';
    }
}
