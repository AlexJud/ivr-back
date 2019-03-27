package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.context.Context;

import java.util.List;

public abstract class Node{
    private List<Relation> edgeList;
    private Context context;
    private Integer repeat;

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

    public abstract Context run();
}
