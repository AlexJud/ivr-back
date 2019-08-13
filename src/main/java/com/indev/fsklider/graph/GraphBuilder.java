package com.indev.fsklider.graph;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indev.fsklider.graph.nodes.*;
import com.indev.fsklider.graph.nodes.properties.ActionProps;
import com.indev.fsklider.graph.nodes.properties.ExtractProps;
import com.indev.fsklider.graph.nodes.properties.ValidateProps;
import com.indev.fsklider.graph.nodes.properties.ValidatePropsVarListItem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class GraphBuilder {
    private String filename;

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
        String filepath = filename + "/src/main/resources" + "/graph.json";
//        String filepath = filename + "/src/main/resources" + "/graph.json";
        JsonNode rootNode = mapper.readValue(new FileInputStream(filepath), JsonNode.class);
        Iterator<JsonNode> iterator = rootNode.elements();
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
//            System.out.println(node.get("type").textValue());
            if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ActionNode) {
                ActionNode objectNode = mapper.treeToValue(node, ActionNode.class);
                graph.put(objectNode.getId(), objectNode);
//            } else if (NodeType.valueOf(node.get("type").textValue()) == NodeType.ANALYSIS) {
//                AnalysisNode objectNode = mapper.treeToValue(node, AnalysisNode.class);
//                graph.put(objectNode.getId(), objectNode);
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
                TransferNode objectNode = mapper.treeToValue(node, TransferNode.class);
                graph.put(objectNode.getId(), objectNode);
//                ArrayList<String> temp = (ArrayList<String>) objectNode.getProps();
//                temp.set(0, temp.get(0).replace(",", "\\,"));
//                objectNode.setProps(temp);
//                System.out.println(objectNode.getProps());
//                graph.put(objectNode.getId(), objectNode);
            } else if(NodeType.valueOf(node.get("type").textValue()) == NodeType.SpecifierNode) {
                for (Node newNode : spreadNode(node)) {
                    graph.put(newNode.getId(), newNode);
                }
            }
        }
        for (Map.Entry entry : graph.entrySet()) {
            System.out.println(entry.getValue());
        }
        return graph;
    }

    private ArrayList<Node> spreadNode(JsonNode node) {
        ValidateNode validateNode = new ValidateNode();
        ArrayList<Node> nodeList = new ArrayList<>();
        nodeList.add(validateNode);
        String specifierId = node.get("id").textValue();
        String currentVarName;

        //Setup Validate Node
        validateNode.setId(specifierId);
        ValidateProps validateProps = new ValidateProps();
        ArrayList<ValidatePropsVarListItem> varListItems = new ArrayList<>();
        for (JsonNode props : node.get("props")) {
            ValidatePropsVarListItem varListItem = new ValidatePropsVarListItem();
            ActionNode actionNode = new ActionNode();
            ExtractNode extractNode = new ExtractNode();
            nodeList.add(actionNode);
            nodeList.add(extractNode);

            currentVarName = props.get("varName").textValue();
            varListItem.setVarName(currentVarName);
            varListItem.setRawVarName("raw_" + currentVarName);
            varListItem.setEdgeIfEmpty(specifierId + "_action_" + props.get("varName").textValue());
            System.out.println(varListItem.getEdgeIfEmpty());

            varListItems.add(varListItem);

            actionNode.setId(specifierId + "_action_" + currentVarName);
            ActionProps actionProps = new ActionProps();
            actionProps.setSynthText(props.get("synthText").textValue().replace(",", "\\,"));
            actionProps.setGrammar(props.get("grammar").textValue());
            actionProps.setOptions(props.get("asrOptions").textValue());
            actionNode.setProps(actionProps);
            Relation actionEdge = new Relation();
            actionEdge.setId(specifierId + "_extract_" + currentVarName);
            ArrayList<Relation> edgeList = new ArrayList<>();
            edgeList.add(actionEdge);
            actionNode.setEdgeList(edgeList);

            extractNode.setId(specifierId + "_extract_" + currentVarName);
            ExtractProps extractProps = new ExtractProps();
            extractProps.setVarName(currentVarName);
            extractProps.setRawVarName("raw_" + currentVarName);
            ArrayList<String> keywords = new ArrayList<>();
            for (JsonNode keyword: props.get("keywords")) {
                keywords.add(keyword.textValue());
            }
            extractProps.setMatch(keywords);
            extractNode.setProps(extractProps);
            Relation extractEdge = new Relation();
            extractEdge.setId(validateNode.getId());
            edgeList = new ArrayList<>();
            edgeList.add(extractEdge);
            extractNode.setEdgeList(edgeList);
        }
        validateProps.setVarList(varListItems);
        validateProps.setEdgeIfSuccess(node.get("edgeList").elements().next().get("id").textValue());
        validateNode.setProps(validateProps);
        return nodeList;
    }
}
