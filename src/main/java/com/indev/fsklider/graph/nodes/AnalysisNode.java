package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.utils.Utils;

import java.util.ArrayList;

public class AnalysisNode extends Node{
    private ArrayList<String> props;

    public ArrayList<String> getProps() {
        return props;
    }

    public void setProps(ArrayList<String> props) {
        this.props = props;
    }

    @Override
    public String run() {
        String recogResult = getContext().getRecogResult();
        for (String match : props) {
            // TODO Добавить для каждого типа свою проверку
            switch (getId()) {
                case "check_estate": {
                    if (recogResult.contains(match)) {
                        getContext().getResult().setEstate(match);
                    }
                    break;
                }
                case "check_name" : {
                    getContext().getResult().setName(Utils.getMessage(getContext().getRecogResult()));
                    break;
                }
                case "check_name_template" : {
                    String name = Utils.getName(getContext().getRecogResult(), props.get(0));
                    getContext().getResult().setName(name);
                    break;
                }
                case "check_source": {
                    getContext().getResult().setSource(match);
                    break;
                }
                case "check_number": {
                    getContext().getResult().setNumber(Utils.getMessage(getContext().getRecogResult()));
                    break;
                }
                case "check_yes": {

                }
                case "check_no": {

                }
                default:
                    getContext().getResult().setReason(match);
                    break;
            }
        }
        return "";
    }
}
