package com.indev.fsklider.dto.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indev.fsklider.commands.SaveToRedMine;
import com.indev.fsklider.commands.SpeechAndHangup;
import com.indev.fsklider.commands.SpeechAndListenCommand;
import com.indev.fsklider.dto.NodeDTO;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.models.Edge;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class NodeDTOConverter {
    public static Dialog convertToDialog(NodeDTO nodeDTO) {
        Dialog dialog = new Dialog();

        dialog.setSynthText(nodeDTO.getProps().getSynthText().replaceAll(",","."));
        dialog.setId(nodeDTO.getId());
        dialog.setAsrOptions(nodeDTO.getProps().getAsrOptions());
        dialog.setGrammar(nodeDTO.getProps().getGrammar());
        dialog.setType(nodeDTO.getType());

//        -------------------- set Commands

//        --------------------- end set

        return dialog;
    }

    public static ArrayList<Edge> getEdges(NodeDTO nodeDTO, Map<String,Edge> varMap){
        ArrayList<Edge> list = new ArrayList<>();

        nodeDTO.getEdgeList().forEach(rec -> {
            Edge edge = new Edge(nodeDTO.getId(),rec.getId());
            edge.setKeyWords(rec.getMatch());
            edge.setName(rec.getName());
//            ArrayList<Edge> edges = edgesMap.get(nodeDTO.getId());
//            if (edges == null){
//                edges =  new ArrayList<>();
//            }
//            edges.add(edge);
//            edgesMap.put(nodeDTO.getId() , edges);
//
//            if (rec.getName() != null){
//                varMap.put(rec.getName(),null);
//            }

            list.add(edge);

            if (edge.getName() != null) {
                varMap.put(edge.getName(),edge);
            }
        });
        return list;
    }


}
