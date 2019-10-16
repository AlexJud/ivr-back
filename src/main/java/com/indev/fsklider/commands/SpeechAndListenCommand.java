package com.indev.fsklider.commands;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.utils.Utils;
import lombok.extern.log4j.Log4j;
import org.asteriskjava.fastagi.AgiException;

@Log4j
public class SpeechAndListenCommand implements Executable {

    private final String COMMAND = "SynthAndRecog";
    private final String OPTIONS = "http://localhost/theme:graph, b=0&t=5000&nit=5000";

    @Override
    public boolean execute(Incoming asterisk, Dialog node) {
        if (!node.getSynthText().isEmpty()) {
            try {
                String textWithVars = Utils.replaceVar(node.getSynthText(),asterisk.getBuilder().getVariableMap());

                asterisk.getSocket().sendServerMessage(textWithVars);
                asterisk.exec(COMMAND, textWithVars, OPTIONS);
                String answer = asterisk.getVariable("RECOG_INPUT(0)");
                log.info("Listener sad: " + answer);

                while (answer == null) {
                    asterisk.exec(COMMAND,"Говорите быстрее и короче. У Вас всего 5 секунд на ответ", OPTIONS);
                    answer = asterisk.getVariable("RECOG_INPUT(0)");
                }

                node.setResultAnswer(answer.equals("<noinput/>") ? "" : answer); // FIXME: 15.10.2019
                asterisk.getSocket().sendUserMessage(asterisk.getVariable("CALLERID(ANI)"),node.getResultAnswer());

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
