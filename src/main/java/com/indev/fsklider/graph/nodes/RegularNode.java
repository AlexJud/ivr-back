package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.results.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RegularNode extends Node {
    private String id;
    private List<String> props;
    private List<Relation> edgeList;
    private Context context;
    private Integer repeat = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getProps() {
        return props;
    }

    public void setProps(List<String> props) {
        this.props = props;
    }

    public List<Relation> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Relation> edgeList) {
        this.edgeList = edgeList;
    }

    @Override
    public Integer getRepeat() {
        return repeat;
    }

    @Override
    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isCommand() {
        return true;
    }

    private void checkUnconditional() {
        if (edgeList != null) {
            if (edgeList.size() == 1) {
                context.setNextId(edgeList.get(0).getId());
            }
        } else {
            context.setNextId(context.getPreviousId());
        }
    }

    private void calculateNext() {
        checkUnconditional();
    }

    @Override
    public Context run() {
        Stack<Command> commandList = context.getCommands();
        Command command = new Command();
        command.setApp("MRCPRecog");
        command.setOption(props.get(1));
        commandList.push(command);
        command = new Command();
        command.setApp("MRCPSynth");
        command.setOption(props.get(0));
        commandList.push(command);
        context.setCommands(commandList);
        System.out.println(commandList); //TODO Delete this debug
        calculateNext();
        return context;
    }

    @Override
    public String toString() {
        return "RegularNode{" +
                "id='" + id + '\'' +
                ", props=" + props +
                ", edgeList=" + edgeList +
                '}';
    }

    public static void main(String[] args) {
        RegularNode node = new RegularNode();
        node.props = new ArrayList<>();
        node.props.add("asdas");
        node.props.add("a111");
        node.run();
    }
}
