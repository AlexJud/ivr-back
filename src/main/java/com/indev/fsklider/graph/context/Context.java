package com.indev.fsklider.graph.context;

import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.graph.results.DialogResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

@Component
@Scope("prototype")
public class Context {
    private HashMap<String, String> contextMap = new HashMap<>();
    private String recogResult;
    private DialogResult result =new DialogResult();
    private ArrayList<String> matchResult = new ArrayList<>();
    private Stack<Command> commands = new Stack<>();
    private String nextId;
    private String previousId;
    private boolean notRepeat = false;
    private boolean isEnd;

    public HashMap<String, String> getContextMap() {
        return contextMap;
    }

    public void setContextMap(HashMap<String, String> contextMap) {
        this.contextMap = contextMap;
    }

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

    public DialogResult getResult() {
        return result;
    }

    public void setResult(DialogResult result) {
        this.result = result;
    }

    public boolean getNotRepeat() {
        return notRepeat;
    }

    public void setNotRepeat(boolean notRepeat) {
        this.notRepeat = notRepeat;
    }
}
