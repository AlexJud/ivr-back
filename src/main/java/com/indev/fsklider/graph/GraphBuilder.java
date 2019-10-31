package com.indev.fsklider.graph;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indev.fsklider.commands.*;
import com.indev.fsklider.commands.system.SaveToRedMine;
import com.indev.fsklider.dto.NodeDTO;
import com.indev.fsklider.dto.converters.NodeDTOConverter;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.models.Edge;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Data
@Log4j
public class GraphBuilder {
    private String filename;
//    private int i = 0;

    private Map<String, ArrayList<Edge>> edgeMap = new HashMap<>();
    private Map<String, Dialog> nodeMap = new HashMap<>();
    private Map<String, Edge> variableMap = new HashMap<>();

    public GraphBuilder(String filename) {
        this.filename = filename;
    }

    public void getGraph() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        String filepath = filename + "/src/main/resources" + "/graph_exec.json";
        InputStream stream;
        File json = new File("graph.json");
        if (Files.exists(json.toPath())){
            stream = new FileInputStream(json);
        } else {
            log.error("Не найдет файла сценария, graph.json");
            stream = null; // FIXME: 31.10.2019 
        }


        JsonNode rootNode = mapper.readValue(stream, JsonNode.class);

//        JsonNode rootNode = mapper.readValue(new FileReader(filepath), JsonNode.class);
        Iterator<JsonNode> iterator = rootNode.elements();

        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
//            String type = node.get("type").textValue();

            NodeDTO dto = mapper.treeToValue(node, NodeDTO.class);

            Dialog dialog = NodeDTOConverter.convertToDialog(dto);
            nodeMap.put(dialog.getId(), dialog);
            ArrayList<Edge> list = NodeDTOConverter.getEdges(dto, variableMap);
            edgeMap.merge(dialog.getId(), list, (o, n) -> {
                o.addAll(n);
                return o;
            });

//           --------------------- add Commands
            if (dto.getProps().getSynthText().length() > 0) {
                if (dto.getEdgeList().size() > 0 && (dto.getProps().getOptions().equals("") || dto.getType().equals("BranchNode"))) {
                    dialog.getOperations().add(new SpeechAndListen(dialog.getGrammar() + ", " + dialog.getAsrOptions()));
                } else if (dto.getEdgeList().size() > 0) {
                    dialog.getOperations().add(new Speech());
                }
            }
            if (dto.getProps().getCommand() != null && dto.getProps().getCommand().length() > 0) {
                try {
                    Class aClass = Class.forName("com.indev.fsklider.commands.system." + dto.getProps().getCommand());
                    Executable ex = (Executable) aClass.getConstructor().newInstance();
                    if (dto.getProps().getOptions() != null){
                        ex.setOptions(dto.getProps().getOptions());
                    }
                    log.warn("Создана системная команда: " + ex.getClass().getSimpleName());

                    dialog.getOperations().add(ex);
                } catch (Exception e) {
                    log.warn("Системная команда не найдена или не наследует интерфейс Executable.class : " + dto.getProps().getCommand());
                }

//                if (dto.getProps().getCommand() != null && dto.getProps().getCommand().length()> 0){
//                    String command = dto.getProps().getCommand();
//                    switch (command.toUpperCase()) {
//                        case "SAVETOREDMINE":
//                            dialog.getOperations().add(new SaveToRedMine(dto.getProps().getOptions()));
//                            break;
//                    }
//                }

            }
            if (dto.getEdgeList().size() == 0) {
                if (dto.getProps().getSynthText().length() > 0) {
                    dialog.getOperations().add(new SpeechAndHangup());
                } else {
                    dialog.getOperations().add(new Hangup());
                }
            }


        }
        this.nodeMap.forEach((k, v) -> System.out.println("-----------NODE " + k + "   value" + v.getOperations()));

    }

//    public static void main(String[] args) {
//        File f = new File("scenario");
//        System.out.println("EXIST "+ Files.exists(f.toPath()));
//        System.out.println("EXIST "+ f.toPath().toAbsolutePath().toString());
//        if (!Files.exists(f.toPath())){
//            try {
//                Files.createDirectory(f.toPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

}
