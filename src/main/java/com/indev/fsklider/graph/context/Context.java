package com.indev.fsklider.graph.context;

import com.indev.fsklider.beans.ResponseEvent;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.graph.results.DialogResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Stack;

@Component
@Scope("prototype")
public class Context {
    private HashMap<String, String> contextMap = new HashMap<>();
    private String recogResult;
    private DialogResult result = new DialogResult();
    private Stack<Command> commands = new Stack<>();
    private String name;
    private String nextId;
    private String previousId;
    private ResponseEvent event = new ResponseEvent();
    private boolean notRepeat = false;
    private boolean isEnd;

    public ResponseEvent getEvent() {
        return event;
    }

    public void setEvent(ResponseEvent event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotRepeat() {
        return notRepeat;
    }

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

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
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
