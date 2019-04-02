package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.context.Context;

import java.util.List;

public abstract class Node{
    private String id;
    private List<Relation> edgeList;
    private Integer repeat;
    private Context context;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Relation> getEdgeList()
    {
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

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public abstract String run();
}
