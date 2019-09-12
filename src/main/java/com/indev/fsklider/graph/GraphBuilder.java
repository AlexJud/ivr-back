package com.indev.fsklider.graph;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indev.fsklider.graph.nodes.*;
import com.indev.fsklider.graph.nodes.properties.ActionProps;
import com.indev.fsklider.graph.nodes.properties.ExtractProps;
import com.indev.fsklider.graph.nodes.properties.ValidateProps;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GraphBuilder {
    private String filename;
    private int i = 0;

    public GraphBuilder(String filename) {
        this.filename = filename;
    }

    public static void main(String[] args) throws IOException {
        GraphBuilder builder = new GraphBuilder(System.getProperty("user.dir"));
        builder.getGraph();
    }

    public Map<String, Node> getGraph() throws IOException {
        HashMap<String, Node> graph = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String filepath = filename + "/src/main/resources" + "/graph_exec.json";
//        String filepath = filename + "/src/main/resources" + "/graph.json";
        JsonNode rootNode = mapper.readValue(new FileInputStream(filepath), JsonNode.class);
        Iterator<JsonNode> iterator = rootNode.elements();
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
//            System.out.println(node.get("type").textValue());
            if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ActionNode) {
                ActionNode objectNode = mapper.treeToValue(node, ActionNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.SystemNode) {
                SystemNode objectNode = mapper.treeToValue(node, SystemNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ClassifierNode) {
                ClassifierNode objectNode = mapper.treeToValue(node, ClassifierNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ExtractNode) {
                ExtractNode objectNode = mapper.treeToValue(node, ExtractNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ValidateNode) {
                ValidateNode objectNode = mapper.treeToValue(node, ValidateNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.SEND) {
                SendNode objectNode = mapper.treeToValue(node, SendNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.EndNode) {
                EndNode objectNode = mapper.treeToValue(node, EndNode.class);
                graph.put(objectNode.getId(), objectNode);
            } else if(NodeType.valueOf(node.get("type").textValue()) == NodeType.SpecifierNode) {
                for (Node newNode : splitSpecifierNode(node)) {
                    graph.put(newNode.getId(), newNode);
                }
            } else if(NodeType.valueOf(node.get("type").textValue()) == NodeType.BranchNode) {
                for (Node newNode : splitBranchNode(node)) {
                    graph.put(newNode.getId(), newNode);
                }
            }
        }
        for (Map.Entry entry : graph.entrySet()) {
            System.out.println(entry.getValue());
        }
        return graph;
    }

    private ArrayList<Node> splitBranchNode(JsonNode node) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ActionNode actionNode = new ActionNode();
        ClassifierNode classifierNode = new ClassifierNode();
        Relation relation = new Relation();
        ArrayList<Node> nodeList = new ArrayList<>();
        nodeList.add(actionNode);
        nodeList.add(classifierNode);

        actionNode.setId(node.get("id").textValue());

        ActionProps actionProps = new ActionProps();
        JsonNode props = node.get("props");
        actionProps.setSynthText(props.get("synthText").textValue());
        actionProps.setGrammar(props.get("grammar").textValue());
        actionProps.setOptions("b=0&t=5000&nit=5000");

        actionNode.setProps(actionProps);
        relation.setId("classifier_after_" + node.get("id").textValue()); //TODO Придумай блин как генерировать ID
        ArrayList<Relation> actionEdges = new ArrayList<>();
        actionEdges.add(relation);
        actionNode.setEdgeList(actionEdges);

        classifierNode.setId("classifier_after_" + node.get("id").textValue());
        ArrayList<Relation> classifierEdges = new ArrayList<>();

        JsonNode edgeList = node.get("edgeList");
        for (JsonNode edge : edgeList) {
            relation = mapper.treeToValue(edge, Relation.class);
            classifierEdges.add(relation);
        }
        classifierNode.setEdgeList(classifierEdges);

        return nodeList;
    }

    private ArrayList<Node> splitSpecifierNode(JsonNode node) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        ValidateNode validateNode = new ValidateNode();
        ActionNode actionNode = new ActionNode();
        ExtractNode extractNode = new ExtractNode();
        ArrayList<Node> nodeList = new ArrayList<>();
        nodeList.add(validateNode);
        nodeList.add(actionNode);
        nodeList.add(extractNode);

        String specifierId = node.get("id").textValue();
        JsonNode props = node.get("props");
        String currentVarName = props.get("varName").textValue();

        ArrayList<Relation> edgeList = new ArrayList<>();

        actionNode.setId(specifierId);
        ActionProps actionProps = new ActionProps();
        actionProps.setSynthText(props.get("synthText").textValue().replace(",", "\\,"));
        actionProps.setGrammar(props.get("grammar").textValue());
        actionProps.setOptions("b=0&t=5000&nit=5000");
        actionNode.setProps(actionProps);
        Relation actionEdge = new Relation();
        actionEdge.setId(specifierId + "_extract_" + currentVarName);
        edgeList.add(actionEdge);
        actionNode.setEdgeList(edgeList);

        extractNode.setId(specifierId + "_extract_" + currentVarName);
        ExtractProps extractProps = new ExtractProps();
        extractProps.setVarName(currentVarName);
        if (props.get("match") != null) {
            ArrayList<String> match = new ArrayList<>();
            for (JsonNode keyword: props.get("match")) {
                match.add(keyword.textValue());
            }
            extractProps.setMatch(match);
        }
        if (props.get("matchFile") != null) {
            extractProps.setMatchFile(props.get("matchFile").textValue());
        }
        extractNode.setProps(extractProps);
        Relation extractEdge = new Relation();
        extractEdge.setId(specifierId + "_" + "validator");
        edgeList = new ArrayList<>();
        edgeList.add(extractEdge);
        extractNode.setEdgeList(edgeList);

                //Setup Validate Node
        validateNode.setId(specifierId + "_" + "validator");
        ValidateProps validateProps = new ValidateProps();
        validateProps.setVarName(props.get("varName").textValue());
        for (JsonNode prop : node.get("edgeIfEmpty")) {
            validateNode.setEdgeIfEmpty(mapper.treeToValue(prop, Relation.class));
        }
        validateNode.setProps(validateProps);
        edgeList = new ArrayList<>();
        for (JsonNode prop : node.get("edgeList")) {
            edgeList.add(mapper.treeToValue(prop, Relation.class));
        }
        validateNode.setEdgeList(edgeList);

        return nodeList;
    }
}
