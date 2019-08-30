package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.nodes.properties.EndProps;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.utils.Utils;

import java.util.Map;
import java.util.Stack;

public class TransferNode extends Node{
//    private List<String> props;
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
//        String message = Utils.getMessage(getContext().getRecogResult());
        String message = getContext().getRecogResult();
        getContext().getContextMap().put("message", message);
        Stack<Command> commandList = getContext().getCommands();
        Command command = new Command();
        command.setApp("MRCPSynth");
        String text = replaceVar(props.getSynthText());
        command.setOption(text);
        getContext().getEvent().setSystemText(Utils.removeBackslash(text));
        commandList.push(command);
        command = new Command();
        command.setApp("Hangup");
        getContext().setCommands(commandList);
        calculateNext();
        return null;
    }

    @Override
    public String toString() {
        return "TransferNode{" +
                "id=" + getId() +
                " props=" + props +
                '}';
    }
}
