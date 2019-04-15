package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.context.Context;

import java.util.List;
import java.util.regex.Pattern;

public abstract class Node{
    private String id;
    private List<Relation> edgeList;
    private Integer repeat = 0;
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

    String replaceVar(String text) {
        Pattern pattern = Pattern.compile("@person#");
        String var = null;
        try {
            var = text.substring(text.indexOf('@') + 1, text.indexOf('#'));
        } catch (Exception e) {
            //swallow
        }
        String result;
        if (getContext().getContextMap().containsKey(var)) {
            result = text.replaceAll(pattern.toString(), getContext().getContextMap().get(var));
        } else
            result = text.replaceAll(pattern.toString(), "");
        return result;
    }

}
