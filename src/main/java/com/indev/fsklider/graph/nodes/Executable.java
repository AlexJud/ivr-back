package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.models.Dialog;

public interface Executable {
    boolean execute (Incoming asterisk, Dialog node);
}
