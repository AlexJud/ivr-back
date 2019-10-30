package com.indev.fsklider.commands;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;

public class Hangup implements Executable {
    @Override
    public boolean execute(Incoming asterisk, Dialog node) {
        asterisk.setHangup(true);
        return true;
    }

    @Override
    public void setOptions(String options) {

    }
}
