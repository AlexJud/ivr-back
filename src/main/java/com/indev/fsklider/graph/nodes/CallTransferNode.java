package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.results.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CallTransferNode extends Node{
    private String id;
    private List<String> props;
    private List<Relation> edgeList;
    private Context context;
    private Integer repeat = 0;
    private ArrayList<String> data;
    public final boolean isCommand = true;

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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Integer getRepeat() {
        return repeat;
    }

    @Override
    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    private void calculateNext() {
        context.setEnd(true);
    }


    @Override
    public Context run() {
        Stack<Command> commandList = context.getCommands();
        Command command = new Command();
        command.setApp("MRCPSynth");
        command.setOption(props.get(0));
        commandList.push(command);
        command = new Command();
        command.setApp("Hangup");
        context.setCommands(commandList);
        System.out.println(context.getMatchResult());
        calculateNext();
        return context;
    }
}
