package com.indev.fsklider.models;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.handlers.excepts.HangUpException;
import com.indev.fsklider.services.GraphBuilderService;

public interface Executable {
    boolean execute(Incoming asterisk, Dialog node, GraphBuilderService graph) throws HangUpException;

    default String getDescription() {
        return this.getClass().getSimpleName();
    }

    void setOptions(String options);
}
