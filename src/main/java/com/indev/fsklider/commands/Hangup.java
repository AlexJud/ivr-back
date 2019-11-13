package com.indev.fsklider.commands;

import com.indev.fsklider.agiscripts.HangUpException;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.commands.options.MRCPFactory;
import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;
import org.asteriskjava.fastagi.AgiException;

public class Hangup implements Executable {
    @Override
    public boolean execute(Incoming asterisk, Dialog node, GraphBuilder graph) throws HangUpException {
//        try {
//            asterisk.exec(MRCPFactory.instance().commands().hangUp());
            throw new HangUpException();
//        } catch (AgiException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
    }

    @Override
    public void setOptions(String options) {

    }
}
