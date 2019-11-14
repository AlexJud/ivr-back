package com.indev.fsklider.commands;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.models.enums.MessageType;
import com.indev.fsklider.commands.options.MRCPCommands;
import com.indev.fsklider.commands.options.MRCPFactory;
import com.indev.fsklider.services.GraphBuilderService;
import com.indev.fsklider.models.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.utils.Utils;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.asteriskjava.fastagi.AgiException;

@Log4j
@NoArgsConstructor
public class SpeechAndListen implements Executable {

    private MRCPCommands commands = MRCPFactory.instance().commands();
//    private String options = commands.options();

//    public SpeechAndListen(String grammar) {
//        this.options = grammar;
//    }

    @Override
    public boolean execute(Incoming asterisk, Dialog node, GraphBuilderService graph) {
        if (!node.getSynthText().isEmpty()) {
            try {
                int repeat = 2;
                String textWithVars = Utils.replaceVar(node.getSynthText(), graph.getVariableMap());

//                asterisk.getSocket().sendServerMessage(textWithVars);
                asterisk.getSocket().sendMessage(textWithVars,MessageType.SERVER,asterisk.getVariable("EXTEN"));
                asterisk.exec(commands.speakAndListen(), textWithVars, node.getGrammar() + ", "+node.getAsrOptions());
                String answer = asterisk.getVariable("RECOG_INPUT(0)");
                log.info("Listener said: " + answer);

                while (answer == null) {
                    asterisk.exec(commands.speakAndListen(), "Говорите быстрее и короче. У Вас всего 5 секунд на ответ", node.getGrammar() + ", "+node.getAsrOptions());
                    answer = asterisk.getVariable("RECOG_INPUT(0)");
                    if (repeat-- == 0) {
                        answer = "";
                        break;
                    }
                }
                if (answer.equals("<noinput/>") || answer.equals("<nomatch/>")) {
                    answer = "";
                }
                node.setResultAnswer(answer);
//                asterisk.getSocket().sendUserMessage(asterisk.getVariable("CALLERID(ANI)"), node.getResultAnswer());
                asterisk.getSocket().sendMessage(node.getResultAnswer(), MessageType.USER, asterisk.getVariable("EXTEN"));

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

//    @Override
//    public String toString() {
//        return "SpeechAndListen, Options:" + options;
//    }
}
