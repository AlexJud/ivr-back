package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.context.Context;

import java.util.ArrayList;
import java.util.List;

public class SendNode extends Node {
    private String id;
    private List<String> props;
    private List<Relation> edgeList;
    private Context context;
    private Integer repeat = 0;
    private ArrayList<String> data;

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

    @Override
    public List<Relation> getEdgeList() {
        return edgeList;
    }

    @Override
    public void setEdgeList(List<Relation> edgeList) {
        this.edgeList = edgeList;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
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

    @Override
    public Context run() {
        System.out.println("User data is: " + data);
        return null;
    }
}
