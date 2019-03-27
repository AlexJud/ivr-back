package com.indev.fsklider.graph.results;

import java.util.List;

public class CommandResult extends Result {
    private List<Command> commands;

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
