package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.results.Command;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Stack;

@Component
public class ActionNode extends Node {
    private List<String> props;
    private Integer repeat = 0;

    public List<String> getProps() {
        return props;
    }

    public void setProps(List<String> props) {
        this.props = props;
    }

    @Override
    public Integer getRepeat() {
        return repeat;
    }

    @Override
    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
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
        if (repeat == 0) {
            command.setApp("MRCPRecog");
            command.setOption(props.get(1));
            commandList.push(command);
            command = new Command();
            command.setApp("MRCPSynth");
            command.setOption(props.get(0));
            commandList.push(command);
        } else {
            command.setApp("MRCPRecog");
            command.setOption("http://localhost/theme:graph,f=beep&b=1&i=any");
            commandList.push(command);
            command = new Command();
            command.setApp("MRCPSynth");
            command.setOption("Пожалуйста\\, повторите");
            commandList.push(command);
            repeat = 0;
            getContext().setNotRepeat(true);
        }

        getContext().setCommands(commandList);
        System.out.println(commandList); //TODO Delete this debug
        repeat++;
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

    public static void main(String[] args) {
//        ActionNode node = new ActionNode();
//        node.props = new ArrayList<>();
//        node.props.add("asdas");
//        node.props.add("a111");
//        node.run();
    }
}
