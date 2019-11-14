package com.indev.fsklider.commands;


import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.models.enums.MessageType;
import com.indev.fsklider.commands.options.MRCPCommands;
import com.indev.fsklider.commands.options.MRCPFactory;
import com.indev.fsklider.services.GraphBuilderService;
import com.indev.fsklider.models.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.utils.Utils;
import lombok.extern.log4j.Log4j;
import org.asteriskjava.fastagi.AgiException;

@Log4j
public class Speech implements Executable {

    //    private static final String COMMAND_SYNTH = "MRCPSynth";
//    private static final  String OPTIONS = "http://localhost/theme:graph, b=0&t=5000&nit=5000";
    private MRCPCommands commands = MRCPFactory.instance().commands();

    @Override
    public boolean execute(Incoming asterisk, Dialog node, GraphBuilderService graph) {

        if (!node.getSynthText().isEmpty()) {
            try {
                String textWithVars = Utils.replaceVar(node.getSynthText(), graph.getVariableMap());

//                asterisk.getSocket().sendServerMessage(textWithVars);
                asterisk.getSocket().sendMessage(textWithVars, MessageType.SERVER,asterisk.getVariable("EXTEN"));
                asterisk.exec(commands.speak(), textWithVars, node.getGrammar() + ", "+node.getAsrOptions());
            } catch (AgiException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        log.warn("Text for synthesis is not specified");
        return false;
    }

    @Override
    public void setOptions(String options) {

    }
}
