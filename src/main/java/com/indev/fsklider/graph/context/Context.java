package com.indev.fsklider.graph.context;

import com.indev.fsklider.graph.results.Command;

import java.util.ArrayList;
import java.util.Stack;

public class Context {
    private String recogResult;
    private ArrayList<String> matchResult = new ArrayList<>();
    private Stack<Command> commands = new Stack<>();
    private String nextId;
    private String previousId;
    private boolean isEnd;

    public String getRecogResult() {
        return recogResult;
    }

    public void setRecogResult(String recogResult) {
        this.recogResult = recogResult;
    }

    public ArrayList<String> getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(ArrayList<String> matchResult) {
        this.matchResult = matchResult;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public Integer getRepeatMax() {
        return 1;
    }

    public Stack<Command> getCommands() {
        return commands;
    }

    public void setCommands(Stack<Command> commands) {
        this.commands = commands;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        setPreviousId(this.nextId);
        this.nextId = nextId;
    }

    public String getPreviousId() {
        return previousId;
    }

    public void setPreviousId(String previousId) {
        this.previousId = previousId;
    }
}
