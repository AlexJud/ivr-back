package com.indev.fsklider.commands;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.handlers.excepts.HangUpException;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.models.Executable;
import com.indev.fsklider.services.GraphBuilderService;

public class Hangup implements Executable {
    @Override
    public boolean execute(Incoming asterisk, Dialog node, GraphBuilderService graph) throws HangUpException {
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
