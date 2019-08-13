package com.indev.fsklider.graph.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.indev.fsklider.graph.nodes.properties.ActionProps;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.utils.Utils;

import java.util.Stack;

public class ActionNode extends Node {

    @JsonProperty("props")
    private ActionProps props;

    public ActionProps getProps() {
        return props;
    }

    public void setProps(ActionProps props) {
        this.props = props;
    }

    private String checkUnconditional() {
        if (getEdgeList() != null) {
            if (getEdgeList().size() == 1) {
                return getEdgeList().get(0).getId();
            }
        }
        return getContext().getPreviousId();
    }

    private String calculateNext() {
        return checkUnconditional();
    }

    @Override
    public String run() {
        Stack<Command> commandList = getContext().getCommands();
        Command command = new Command();
        if (getRepeat() == 0) {
            command.setApp("SynthAndRecog");
            String text = replaceVar(props.getSynthText());
            command.setOption(text + ", " +  props.getGrammar() + ", " + props.getOptions());
            getContext().getEvent().setSystemText(Utils.removeBackslash(text));
            commandList.push(command);
        } else {
            command.setApp("SynthAndRecog");
            command.setOption("Пожалуйста\\, повторите," + "http://localhost/theme:graph,b=1&t=5000&nit=5000");
//            command.setOption("Пожалуйста\\, повторите," + "l=ru-RU&p=uni2");
            getContext().getEvent().setSystemText("Пожалуйста, повторите");
            commandList.push(command);
            setRepeat(0);
            getContext().setNotRepeat(true);
        }

        getContext().setCommands(commandList);
        setRepeat(getRepeat() + 1);
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
