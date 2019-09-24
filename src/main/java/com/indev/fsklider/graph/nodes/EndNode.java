package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.properties.EndProps;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.utils.Utils;
import org.apache.log4j.Logger;

import java.util.Stack;

public class EndNode extends Node{
    private EndProps props;
    private static final Logger log = Logger.getLogger(Incoming.class);
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
        log.info("Выполняется " + this.getClass().getSimpleName() + ": " + getId());
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
        return "8cedc6ebd4ae";
    }

    @Override
    public String toString() {
        return "EndNode{" +
                "id=" + getId() +
                " props=" + props +
                '}';
    }
}
