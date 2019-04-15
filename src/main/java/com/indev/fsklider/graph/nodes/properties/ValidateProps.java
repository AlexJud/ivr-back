package com.indev.fsklider.graph.nodes.properties;

import java.util.ArrayList;

public class ValidateProps {
    private ArrayList<ValidatePropsVarListItem> varList;
    private String edgeIfSuccess;

    public ArrayList<ValidatePropsVarListItem> getVarList() {
        return varList;
    }

    public void setVarList(ArrayList<ValidatePropsVarListItem> varList) {
        this.varList = varList;
    }

    public String getEdgeIfSuccess() {
        return edgeIfSuccess;
    }

    public void setEdgeIfSuccess(String edgeIfSuccess) {
        this.edgeIfSuccess = edgeIfSuccess;
    }
}
