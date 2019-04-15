package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.utils.Utils;

import java.util.List;
import java.util.Stack;

public class TransferNode extends Node{
    private List<String> props;

    public List<String> getProps() {
        return props;
    }

    public void setProps(List<String> props) {
        this.props = props;
    }

    private void calculateNext() {
        getContext().setEnd(true);
    }


    @Override
    public String run() {
        String message = Utils.getMessage(getContext().getRecogResult());
        getContext().getContextMap().put("message", message);
        Stack<Command> commandList = getContext().getCommands();
        Command command = new Command();
        command.setApp("MRCPSynth");
        String text = replaceVar(props.get(0));
        command.setOption(text);
        getContext().getEvent().setSystemText(text);
        commandList.push(command);
        command = new Command();
        command.setApp("Hangup");
        getContext().setCommands(commandList);
        calculateNext();
        return null;
    }

}
