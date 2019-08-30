package com.indev.fsklider.graph.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.properties.ExtractProps;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class ExtractNode extends Node {

    @JsonProperty("props")
    private ExtractProps props;
    private static final Logger log = Logger.getLogger(Incoming.class);

    public ExtractProps getProps() {
        return props;
    }

    public void setProps(ExtractProps props) {
        this.props = props;
    }

    @Override
    public String run() {
        @NotNull String asrResult = getContext().getRecogResult();
//        getContext().getContextMap().put(props.getRawVarName(), asrResult);

        //Проверяем есть ли файл с ключами
        if (props.getMatchFile() != null) {
            log.info("Выбрано сопоставление с файлом ключей");
            validateFromFile(props.getMatchFile(), asrResult);
        //Проверяем есть ли список ключей
        } else if (props.getMatch() != null) {
            log.info("Выбрано сопоставление по масиву ключей");
            validateFromList(asrResult);
        //Если ничего нет и сравнивать не с чем, записываем ответ как есть
        } else {
            log.info("Выбрана запись ответа без сравнения");
            getRawRecognize(asrResult);
        }
        log.info("Контекст: " + Collections.singletonList(getContext().getContextMap()));
        return getEdgeList().get(0).getId();
    }

    private void validateFromFile(String fileName, String asrResult) {
        try {
            File keyFile = new File(System.getProperty("user.dir") + "/src/main/resources/" + fileName);
            List<String> keys = Files.readAllLines(keyFile.toPath());
            ArrayList<String> strings = new ArrayList<>();
            for (String key : keys) {
                if (asrResult.contains(key)) {
                    strings.add(key);
                    strings.sort(Comparator.comparingInt(s -> Math.abs(s.length() - "intelligent".length())));
                    insert(props.getVarName(), strings.get(0));
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void validateFromList(String asrResult) {
        for (String key : props.getMatch()) {
            if (asrResult.contains(key)) {
                insert(props.getVarName(), key);
            }
        }
    }
    //Если VN вернул <nomatch/>, то не записываем это в переменную.
    private void getRawRecognize(@NotNull String asrResult) {
        if(!asrResult.equals("<nomatch/>")) {
            getContext().getContextMap().put(props.getVarName(), asrResult);
        }
    }

    @Override
    public String toString() {
        return "ExtractNode{" +
                "id= " + getId() +
                " EdgeList= " + getEdgeList() +
                " props=" + props +
                '}';
    }
}
