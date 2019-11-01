package com.indev.fsklider.dto.converters;

import com.indev.fsklider.commands.options.MRCPFactory;
import com.indev.fsklider.dto.NodeDTO;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.models.Edge;

import java.util.ArrayList;
import java.util.Map;

public class NodeDTOConverter {
    public static Dialog convertToDialog(NodeDTO nodeDTO) {
        Dialog dialog = new Dialog();

        dialog.setSynthText(nodeDTO.getProps().getSynthText().replaceAll(",","."));
        dialog.setId(nodeDTO.getId());
        dialog.setAsrOptions(nodeDTO.getProps().getAsrOptions());
//        dialog.setGrammar(nodeDTO.getProps().getGrammar());
        dialog.setGrammar(MRCPFactory.instance().commands().grammar());
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

            list.add(edge);

            if (edge.getName() != null) {
                varMap.put(edge.getName(),edge);
            }
        });
        return list;
    }


}
