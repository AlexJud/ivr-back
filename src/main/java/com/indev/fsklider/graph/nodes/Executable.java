package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.agiscripts.HangUpException;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.models.Dialog;

public interface Executable {
    boolean execute(Incoming asterisk, Dialog node, GraphBuilder graph) throws HangUpException;

    default String getDescription() {
        return this.getClass().getSimpleName();
    }

    void setOptions(String options);
}
