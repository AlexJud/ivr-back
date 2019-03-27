package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemNode extends Node{
    private String id;
    private List<Relation> edgeList;
    private Context context;
    private Integer repeat = 0;
    private String next;
    private boolean estate = false;
    private boolean keyWord = false;
    public final boolean isCommand = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Relation> getEdgeList() {
        return edgeList;
    }

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

    @Override
    public String toString() {
        return "SystemNode{" +
                "id='" + id + '\'' +
                ", edgeList=" + edgeList +
                ", context=" + context +
                ", repeat=" + repeat +
                ", next='" + next + '\'' +
                ", isCommand=" + isCommand +
                '}';
    }

    private boolean checkUnconditional() {
        if (edgeList != null) {
            if (edgeList.size() == 1) {
                context.setNextId(edgeList.get(0).getId());
                return true;
            }
            return false;
        } else {
            context.setNextId(context.getPreviousId());
            return true;
        }
    }

    @Override
    public Context run() {
        if (checkUnconditional()) {
            return context;
        }
        String recogResult = context.getRecogResult();
        String missMatchId = null;
        String transferId = null;
        for (Relation relation : edgeList) {
            if (relation.getMatch() == null) {
                transferId = relation.getId();
            } else if (relation.getMatch().get(0).equals("-")) {
                 missMatchId = relation.getId();
//                continue;
            } else {
                HashMap<String, ArrayList<String>> map = relation.getMatchGroup();
                for (String match : relation.getMatch()) {
                    if (recogResult.contains(match)) {
                        ArrayList<String> matchResult = context.getMatchResult();
                        matchResult.add(match);
                        context.setMatchResult(matchResult);
                        context.setNextId(relation.getId());
                        estate = true;
                        return context;
                    }
                }
            }
        }
        if (repeat < context.getRepeatMax()) {
            repeat++;
            context.setNextId(missMatchId != null ? missMatchId : transferId);
        } else {
            context.setNextId(transferId);
        }
        return context;
    }
}
