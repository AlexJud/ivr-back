package com.indev.fsklider.commands;


import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.commands.options.MRCPCommands;
import com.indev.fsklider.commands.options.MRCPFactory;
import com.indev.fsklider.graph.nodes.Executable;
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
    public boolean execute(Incoming asterisk, Dialog node) {

        if (!node.getSynthText().isEmpty()) {
            try {
                String textWithVars = Utils.replaceVar(node.getSynthText(), asterisk.getBuilder().getVariableMap());

                asterisk.getSocket().sendServerMessage(textWithVars);
                asterisk.exec(commands.speak(), textWithVars, commands.options());
            } catch (AgiException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        log.warn("Text for synthesis is not specified");
        return false;
    }
}