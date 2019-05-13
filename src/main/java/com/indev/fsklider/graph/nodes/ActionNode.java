package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.results.Command;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Stack;

@Component
public class ActionNode extends Node {
    private List<String> props;

    public List<String> getProps() {
        return props;
    }

    public void setProps(List<String> props) {
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
            String text = replaceVar(props.get(0));
            command.setOption(text + ", " +  props.get(1));
            getContext().getEvent().setSystemText(text);
            commandList.push(command);
        } else {
            command.setApp("SynthAndRecog");
//            command.setOption("Пожалуйста\\, повторите," + "http://localhost/theme:graph,b=1&t=5000&nit=5000");
            command.setOption("Пожалуйста\\, повторите," + "l=ru-RU&p=uni2");
            getContext().getEvent().setSystemText("Пожалуйста, повторите");
            commandList.push(command);
            setRepeat(0);
            getContext().setNotRepeat(true);
        }

        getContext().setCommands(commandList);
        setRepeat(getRepeat() + 1);
        return calculateNext();
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
