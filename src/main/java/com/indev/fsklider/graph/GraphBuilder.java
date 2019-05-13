package com.indev.fsklider.graph;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indev.fsklider.graph.nodes.*;

import java.io.FileInputStream;
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
        String filepath = filename + "/src/main/resources" + "/graph_google.json";
//        String filepath = filename + "/src/main/resources" + "/graph.json";
        JsonNode rootNode = mapper.readValue(new FileInputStream(filepath), JsonNode.class);
        Iterator<JsonNode> iterator = rootNode.elements();
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
//            System.out.println(node.get("type").textValue());
            if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ACTION) {
                ActionNode objectNode = mapper.treeToValue(node, ActionNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ANALYSIS) {
                AnalysisNode objectNode = mapper.treeToValue(node, AnalysisNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.CLASSIFIER) {
                ClassifierNode objectNode = mapper.treeToValue(node, ClassifierNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.EXTRACT) {
                ExtractNode objectNode = mapper.treeToValue(node, ExtractNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.VALIDATE) {
                ValidateNode objectNode = mapper.treeToValue(node, ValidateNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.SEND) {
                SendNode objectNode = mapper.treeToValue(node, SendNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.TRANSFER) {
                TransferNode objectNode = mapper.treeToValue(node, TransferNode.class);
                graph.put(objectNode.getId(), objectNode);
            }
        }
//        for (Map.Entry entry : graph.entrySet()) {
//            System.out.println(entry.getValue());
//        }
        return graph;
    }
}
