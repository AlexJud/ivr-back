package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.utils.Utils;

import java.util.ArrayList;

public class AnalysisNode extends Node{
    private String id;
    private ArrayList<String> props;
    private Context context;
    private Integer repeat = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getProps() {
        return props;
    }

    public void setProps(ArrayList<String> props) {
        this.props = props;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Integer getRepeat() {
        return repeat;
    }

    @Override
    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    @Override
    public String run() {
        String recogResult = context.getRecogResult();
        for (String match : props) {
            // TODO Добавить для каждого типа свою проверку
            switch (id) {
                case "check_estate": {
                    if (recogResult.contains(match)) {
                        context.getResult().setEstate(match);
                    }
                    break;
                }
                case "check_name" : {
                    context.getResult().setName(Utils.getMessage(context.getRecogResult()));
                    break;
                }
                case "check_name_template" : {
                    String name = Utils.getName(context.getRecogResult(), props.get(0));
                    context.getResult().setName(name);
                    break;
                }
                case "check_source": {
                    context.getResult().setSource(match);
                    break;
                }
                case "check_number": {
                    context.getResult().setNumber(Utils.getMessage(context.getRecogResult()));
                    break;
                }
                case "check_yes": {

                }
                case "check_no": {

                }
                default:
                    context.getResult().setReason(match);
                    break;
            }
        }
        return "";
    }
//    public Context run() {
//        if (checkUnconditional()) {
//            return context;
//        }
//        String recogResult = context.getRecogResult();
//        String missMatchId = null;
//        String transferId = null;
//        for (Relation relation : edgeList) {
//            if (relation.getMatch() == null) {
//                transferId = relation.getId();
//            } else if (relation.getMatch().get(0).equals("-")) {
//                 missMatchId = relation.getId();
////                continue;
//            } else {
//                HashMap<String, ArrayList<String>> map = relation.getMatchGroup();
//                for (String match : relation.getMatch()) {
//                    if (recogResult.contains(match)) {
//                        ArrayList<String> matchResult = context.getMatchResult();
//                        matchResult.add(match);
//                        context.setMatchResult(matchResult);
//                        context.setNextId(relation.getId());
//                        estate = true;
//                        return context;
//                    }
//                }
//            }
//        }
//        if (repeat < context.getRepeatMax()) {
//            repeat++;
//            context.setNextId(missMatchId != null ? missMatchId : transferId);
//        } else {
//            context.setNextId(transferId);
//        }
//        return context;
//    }
}
