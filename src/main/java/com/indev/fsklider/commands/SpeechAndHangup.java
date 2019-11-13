package com.indev.fsklider.commands;

import com.indev.fsklider.agiscripts.HangUpException;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.beans.socket.MessageType;
import com.indev.fsklider.commands.options.MRCPCommands;
import com.indev.fsklider.commands.options.MRCPFactory;
import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.utils.Utils;
import lombok.extern.log4j.Log4j;
import org.asteriskjava.fastagi.AgiException;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j
public class SpeechAndHangup implements Executable {

    private MRCPCommands commands = MRCPFactory.instance().commands();

    @Override
    public boolean execute(Incoming asterisk, Dialog node, GraphBuilder graph) throws HangUpException {
        if (!node.getSynthText().isEmpty()) {
            try {
                String textWithVars = Utils.replaceVar(node.getSynthText(), graph.getVariableMap());

//                asterisk.getSocket().sendServerMessage(textWithVars);
                asterisk.getSocket().sendMessage(textWithVars, MessageType.SERVER, asterisk.getVariable("EXTEN"));
                asterisk.exec(commands.speak(), textWithVars, node.getGrammar() + ", " + node.getAsrOptions());
                asterisk.exec(commands.hangUp());
//                asterisk.setHangup(true);
                throw new HangUpException();
            } catch (AgiException e) {
                e.printStackTrace();
                return false;
            }
//            return true;
        }

        log.warn("Text for synthesis is not specified");
        return false;
    }

    @Override
    public void setOptions(String options) {

    }

    public static boolean errorRecognize(Incoming asterisk) {
        try {
//            asterisk.exec(COMMAND_SYNTH, "К сожалению не удалось распознать ваш ответ. Звонок будет завершён.", OPTIONS);
            asterisk.exec(MRCPFactory.instance().commands().speak(), "К сожалению не удалось распознать ваш ответ. Звонок будет завершён.");
            asterisk.exec(MRCPFactory.instance().commands().hangUp());
        } catch (AgiException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

//    @Override
//    public String toString() {
//        System.out.println("COMMAND -" + COMMAND_SYNTH);
//        return super.toString();
//    }
}
