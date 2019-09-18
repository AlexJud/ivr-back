package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.nodes.properties.EndProps;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.utils.Utils;

import java.util.Map;
import java.util.Stack;

public class EndNode extends Node{
    private EndProps props;

    public EndProps getProps() {
        return props;
    }

    public void setProps(EndProps props) {
        this.props = props;
    }

    private void calculateNext() {
        getContext().setEnd(true);
    }

    @Override
    public String run() {
        Stack<Command> commandList = getContext().getCommands();
        Command command = new Command();
        command.setApp("Hangup");
        command.setOption("");
        commandList.push(command);

        command = new Command();
        command.setApp("MRCPSynth");
        String text = replaceVar(props.getSynthText());
        command.setOption(text);
        getContext().getEvent().setSystemText(Utils.removeBackslash(text));
        commandList.push(command);

        getContext().setCommands(commandList);
        getContext().setEnd(true);
        calculateNext();
        return "root";
    }

    @Override
    public String toString() {
        return "EndNode{" +
                "id=" + getId() +
                " props=" + props +
                '}';
    }
}
