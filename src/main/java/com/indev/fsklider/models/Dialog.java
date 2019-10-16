package com.indev.fsklider.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.graph.nodes.Node;
import com.indev.fsklider.graph.nodes.properties.ActionProps;
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

    public boolean run(Incoming asterisk) {
        boolean result = true;
        for (Executable operation : this.operations) {
            if (!operation.execute(asterisk, this)) {
                log.warn("Ошибка выполнения команды: " + operations.toString());
                result = false;
            }
        }
        return result;
    }

}
