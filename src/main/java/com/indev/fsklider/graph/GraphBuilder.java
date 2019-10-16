package com.indev.fsklider.graph;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.commands.SaveToRedMine;
import com.indev.fsklider.commands.SpeechAndHangup;
import com.indev.fsklider.commands.SpeechAndListenCommand;
import com.indev.fsklider.dto.NodeDTO;
import com.indev.fsklider.dto.converters.NodeDTOConverter;
import com.indev.fsklider.graph.nodes.*;
import com.indev.fsklider.graph.nodes.properties.ActionProps;
import com.indev.fsklider.graph.nodes.properties.ExtractProps;
import com.indev.fsklider.graph.nodes.properties.ValidateProps;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.models.Edge;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Data
public class GraphBuilder {
    private String filename;
    private int i = 0;

    private Map<String, ArrayList<Edge>> edgeMap = new HashMap<>();
    private Map<String, Dialog> nodeMap = new HashMap<>();
    private Map<String, Edge> variableMap = new HashMap<>();

    public GraphBuilder(String filename) {
        this.filename = filename;
    }

//    public static void main(String[] args) throws IOException {
//        GraphBuilder builder = new GraphBuilder(System.getProperty("user.dir"));
//        builder.getGraph();
//    }

    public void getGraph() throws IOException {
//        Map<String, Node> graph = new HashMap<>();
//        Map<String, ArrayList<Edge>> edges = new HashMap<>();
//        Map<String, String> variableMap = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String filepath = filename + "/src/main/resources" + "/graph_exec.json";
//        String filepath = filename + "/src/main/resources" + "/graph.json";
        JsonNode rootNode = mapper.readValue(new FileInputStream(filepath), JsonNode.class);
        Iterator<JsonNode> iterator = rootNode.elements();

        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
            String type = node.get("type").textValue();

            NodeDTO dto = mapper.treeToValue(node, NodeDTO.class);
            Dialog dialog = NodeDTOConverter.convertToDialog(dto);
            nodeMap.put(dialog.getId(), dialog);
            ArrayList<Edge> list = NodeDTOConverter.getEdges(dto, variableMap);
            edgeMap.merge(dialog.getId(), list, (o, n) -> {
                o.addAll(n);
                return o;
            });
            
            switch (type) {
                case "BranchNode":
                   dialog.getOperations().add(new SpeechAndListenCommand());
                    break;
                case "EndNode":
                    dialog.getOperations().add(new SpeechAndHangup());
                    break;
                case "SystemNode":
                    dialog.getOperations().add(new SaveToRedMine(dto.getProps().getOptions()));     // FIXME: 16.10.2019               
            }

//            if (node.get("type").textValue().equals("BranchNode")) {
//
//                
//
//            } else if()

//
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ActionNode) {
//                ActionNode objectNode = mapper.treeToValue(node, ActionNode.class);
//                nodeMap.put(objectNode.getId(), objectNode);
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.SystemNode) {
//                SystemNode objectNode = mapper.treeToValue(node, SystemNode.class);
//                nodeMap.put(objectNode.getId(), objectNode);
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ClassifierNode) {
//                ClassifierNode objectNode = mapper.treeToValue(node, ClassifierNode.class);
//                nodeMap.put(objectNode.getId(), objectNode);
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ExtractNode) {
//                ExtractNode objectNode = mapper.treeToValue(node, ExtractNode.class);
//                nodeMap.put(objectNode.getId(), objectNode);
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ValidateNode) {
//                ValidateNode objectNode = mapper.treeToValue(node, ValidateNode.class);
//                nodeMap.put(objectNode.getId(), objectNode);
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.SEND) {
//                SendNode objectNode = mapper.treeToValue(node, SendNode.class);
//                nodeMap.put(objectNode.getId(), objectNode);
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.EndNode) {
//                EndNode objectNode = mapper.treeToValue(node, EndNode.class);
//                nodeMap.put(objectNode.getId(), objectNode);
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.SpecifierNode) {
//                for (Node newNode : splitSpecifierNode(node)) {
//                    nodeMap.put(newNode.getId(), newNode);
//                }
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.BranchNode) {
//                for (Node newNode : splitBranchNode(node)) {
//                    graph.put(newNode.getId(), newNode);
//                }
//            }
        }
        for (Map.Entry entry : nodeMap.entrySet()) {
            System.out.println("GRAPH " + entry.getValue());

        }

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
        actionNode.setJId(node.get("id").textValue());

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
        classifierNode.setJId("classifier_after_" + node.get("id").textValue());
        ArrayList<Relation> classifierEdges = new ArrayList<>();

        JsonNode edgeList = node.get("edgeList");
        boolean isDefaultExist = false;
        for (JsonNode edge : edgeList) {
            relation = mapper.treeToValue(edge, Relation.class);
            if (relation.getMatch() == null || relation.getMatch().size() == 0) {
                isDefaultExist = true;
            }
            classifierEdges.add(relation);
        }
        if (!isDefaultExist) {
            relation = new Relation();
            relation.setId(node.get("id").textValue());
            classifierEdges.add(relation);
        }
        classifierNode.setEdgeList(classifierEdges);

        return nodeList;
    }

    private ArrayList<Node> splitSpecifierNode(JsonNode node) throws IOException {
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
        String currentVarName = props.get("varName").textValue().trim();

        ArrayList<Relation> edgeList = new ArrayList<>();

        actionNode.setId(specifierId);
        actionNode.setJId(specifierId);
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
        extractNode.setJId(specifierId);
        ExtractProps extractProps = new ExtractProps();
        extractProps.setVarName(currentVarName);
        Relation[] relations = mapper.treeToValue(node.get("edgeList"), Relation[].class);
        for (Relation relation : relations) {
            extractProps.setMatch(relation.getMatch());
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
        validateNode.setJId(specifierId);
        ValidateProps validateProps = new ValidateProps();
        validateProps.setVarName(props.get("varName").textValue().trim());

        Relation relation = new Relation();
        if (node.get("edgeIfEmpty") == null) {
            relation.setId(specifierId);
            validateNode.setEdgeIfEmpty(relation);
        } else {
            relations = mapper.treeToValue(node.get("edgeIfEmpty"), Relation[].class);
            if (relations.length == 0) {
                relation.setId(specifierId);
                validateNode.setEdgeIfEmpty(relation);
            } else {
                for (JsonNode prop : node.get("edgeIfEmpty")) {
                    validateNode.setEdgeIfEmpty(mapper.treeToValue(prop, Relation.class));
                }
            }
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
