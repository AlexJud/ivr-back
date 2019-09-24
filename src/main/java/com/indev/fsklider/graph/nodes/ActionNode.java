package com.indev.fsklider.graph.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.properties.ActionProps;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Stack;
@Getter @Setter
public class ActionNode extends Node {

    @JsonProperty("props")
    private ActionProps props;

    private static final Logger log = Logger.getLogger(Incoming.class);

    private String calculateNext() {
        return getEdgeList().get(0).getId();
    }

    @Override
    public String run() {
        log.info("Выполняется " + this.getClass().getSimpleName() + ": " + getId());
        String text = replaceVar(props.getSynthText());
        if (getRepeatCount() == 3) {
            text = replaceVar("К сожалению, не удалось распознать ваш ответ. Звонок будет завершён.");
            hangup();
            setRepeatCount(0);
        }
        synthesizeAndRecognize(text);
        setRepeatCount(getRepeatCount() + 1);
        String nextId = calculateNext();
        log.info("Node: " + getId() + " - завершил выполнение");
        log.info("Содержимое контекста: " + Collections.singletonList(getContext()));
        log.info("Следующий Node: " + nextId + "\n");
        return nextId;
    }

    private void synthesizeAndRecognize(String text) {
        Stack<Command> commandList = getContext().getCommands();
        Command command = new Command();
        command.setApp("SynthAndRecog");
        command.setOption(text + ", " +  props.getGrammar() + ", " + props.getOptions());
        getContext().getEvent().setSystemText(Utils.removeBackslash(text));
        commandList.push(command);
        getContext().setCommands(commandList);
    }

    private void hangup() {
        getContext().setEnd(true);
    }

    public String getMessage() {
        return getContext().getEvent().getSystemText();
    }

    @Override
    public String toString() {
        return "ActionNode{" +
                "id='" + getId() + '\'' +
                ", props=" + props +
                ", edgeList=" + getEdgeList() +
                '}';
    }
}
