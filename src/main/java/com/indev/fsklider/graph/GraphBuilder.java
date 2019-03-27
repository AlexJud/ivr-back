package com.indev.fsklider.graph;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indev.fsklider.graph.nodes.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GraphBuilder {
    private String filename;

    public GraphBuilder(String filename) {
        this.filename = filename;
    }

    public static void main(String[] args) throws Exception {
        GraphBuilder builder = new GraphBuilder(System.getProperty("user.dir"));
        builder.getGraph();
    }

    public Map<String, Node> getGraph() throws Exception {
        HashMap<String, Node> graph = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String filepath = filename + "/src/main/resources" + "/test_graph.json";
        JsonNode rootNode = mapper.readValue(new FileInputStream(filepath), JsonNode.class);
        Iterator<JsonNode> iterator = rootNode.elements();
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
//            System.out.println(node.get("type").textValue());
            if (NodeType.valueOf(node.get("type").textValue()) == NodeType.REGULAR) {
                RegularNode objectNode = mapper.treeToValue(node, RegularNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.SYSTEM) {
                SystemNode objectNode = mapper.treeToValue(node, SystemNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.SEND) {
                SendNode objectNode = mapper.treeToValue(node, SendNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.TRANSFER) {
                CallTransferNode objectNode = mapper.treeToValue(node, CallTransferNode.class);
                graph.put(objectNode.getId(), objectNode);
            }
        }
//        for (Map.Entry entry : graph.entrySet()) {
//            System.out.println(entry.getValue());
//        }
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        map = graph.get("check_words").getEdgeList().get(0).getMatchGroup();
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            for (String match : entry.getValue()) {

            }
        }
        System.out.println();
        return graph;
    }
}
