package com.indev.fsklider.graph.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.indev.fsklider.graph.nodes.properties.ExtractProps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ExtractNode extends Node {

    @JsonProperty("props")
    private ExtractProps props;

    public ExtractProps getProps() {
        return props;
    }

    public void setProps(ExtractProps props) {
        this.props = props;
    }

    @Override
    public String run() {
        String asrResult = getContext().getRecogResult();
        // Проверяем на повтор
        if (getContext().getNotRepeat()) {
            getRawRecognize(asrResult);
            return getEdgeList().get(0).getId();
        }
        if (props.getMatchFile() != null) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources" + "/russian_names.csv"));
                ArrayList<String> strings = new ArrayList<>();
                while (in.ready()) {
                    String name = in.readLine();
                    if (asrResult.contains(name)) {
                        strings.add(name);
                        strings.sort(Comparator.comparingInt(s -> Math.abs(s.length() - "intelligent".length())));
                        getContext().getContextMap().put(props.getVarName(), strings.get(0));
                        System.out.println("+++++++++++ CONTEXT FROM ExtractNode ++++++++++ " + getContext().getContextMap());
                        getContext().setName(strings.get(0));
//                        contextMap.put();
//                        getContext().setContextMap(contextMap);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (props.getMatch() != null) {
            for (String match : props.getMatch()) {
                if (asrResult.contains(match)) {
                    getContext().getContextMap().put(props.getVarName(), match);
//                    contextMap.put();
//                    getContext().setContextMap(contextMap);
                }
            }
        } else {
            getRawRecognize(asrResult);
        }
        return getEdgeList().get(0).getId();
    }

    public void getRawRecognize(String asrResult) {
//        String message = Utils.getMessage(asrResult);
        String message = asrResult;
        getContext().getContextMap().put(props.getRawVarName(), message);
        getContext().setNotRepeat(false);
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
