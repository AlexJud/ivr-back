package com.indev.fsklider.commands;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.utils.Utils;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.asteriskjava.fastagi.AgiException;

@Log4j
@NoArgsConstructor
public class SpeechAndListenCommand implements Executable {

    private final String COMMAND = "SynthAndRecog";
    private String options = "http://localhost/theme:graph, b=0&t=5000&nit=5000";
//    private final String OPTIONS2 = "/etc/asterisk/grammars/new2.xml, b=0&t=5000&nit=5000";

    public SpeechAndListenCommand(String grammar) {
        this.options = grammar + ", b=0&t=5000&nit=5000";
    }

    @Override
    public boolean execute(Incoming asterisk, Dialog node) {
        if (!node.getSynthText().isEmpty()) {
            try {
                int repeat = 2;
                String textWithVars = Utils.replaceVar(node.getSynthText(),asterisk.getBuilder().getVariableMap());

                asterisk.getSocket().sendServerMessage(textWithVars);
                asterisk.exec(COMMAND, textWithVars, options);
                String answer = asterisk.getVariable("RECOG_INPUT(0)");
                log.info("Listener sad: " + answer);

                while (answer == null) {
                    asterisk.exec(COMMAND,"Говорите быстрее и короче. У Вас всего 5 секунд на ответ", options);
                    answer = asterisk.getVariable("RECOG_INPUT(0)");
                    if (repeat-- == 0) {
                        answer = "";
                        break;
                    }
                }
                if (answer.equals("<noinput/>" ) || answer.equals("<nomatch/>")){
                    answer = "";
                }
                node.setResultAnswer(answer);
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

    @Override
    public String toString() {
        return "SpeechAndListenCommand, Options:"+ options;
    }
}
