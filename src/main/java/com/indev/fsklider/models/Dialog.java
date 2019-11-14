package com.indev.fsklider.models;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.handlers.excepts.HangUpException;
import com.indev.fsklider.services.GraphBuilderService;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;

@Log4j
@Data
public class Dialog {

    private String id;
    private String synthText;
    private String grammar;
    private String asrOptions;
    private ArrayList<Executable> operations = new ArrayList<>();
    private String type;
    private String options;
    private String resultAnswer;

    public boolean run(Incoming asterisk, GraphBuilderService graph) throws HangUpException {
        boolean result = true;
        for (Executable operation : this.operations) {
            boolean execute = operation.execute(asterisk, this, graph);
            if (!execute) {
                log.warn("Ошибка выполнения команды: " + operations.toString());
                result = false;
            }
        }
        return result;
    }

}
