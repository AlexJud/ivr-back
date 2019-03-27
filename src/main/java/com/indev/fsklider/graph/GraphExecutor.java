package com.indev.fsklider.graph;

import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.Node;

import java.util.ArrayList;
import java.util.Map;

public class GraphExecutor {
    private Map<String, Node> graph;
    private Node currentNode;
    private Context context;
    private Integer repeat = 0;
    private ArrayList<String> data;

    public GraphExecutor(Map<String, Node> graph) {
        this.graph = graph;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Node getNext() {
        if (context.getNextId() == null) {
            return graph.get("root");
        }
        if (context.getNextId().equals("end")) {

        }
        return graph.get(context.getNextId());
    }

//    public Node getNext() {
//
//        if (context.isEnd()) {
//            currentNode = graph.get("root");
//            context.setEnd(false);
//            return currentNode;
//        }
//        List<Relation> edgeList = currentNode.getEdgeList();
//        //Начинаем проверять условия для перехода
//        if (edgeList != null) {
//            //Безусловный переход
//            if (edgeList.size() == 1) {
//                //Вытащить ID ноды
//                String id = edgeList.get(0).getId();
//                currentNode = graph.get(id);
//                return currentNode;
//            } else {
//                String missMatchId = null;
//                for (Relation map : edgeList) {
//                    if (map.getMatch() != null) {
//                        if (map.getId().equals("last")) {
//                            return currentNode;
//                        }
//                        for (String string : map.getMatch()) {
//                            if (string.equals("-")) {
//                                missMatchId = map.getId();
//                                continue;
//                            }
//                            System.out.println(string);
//                            if (context.getRecogResult().contains(string)) {
//                                data.add(string);
//                                String id = map.getId();
//                                currentNode = graph.get(id);
//                                return currentNode;
//                            }
//                        }
//                        if (map.getRepeatMax() != null) {
//                            //Проверим, чтобы узел не выполнялся больше N раз
//                            if (currentNode.getRepeat() < map.getRepeatMax()) {
//                                currentNode.setRepeat(currentNode.getRepeat() + 1);
//                                if (missMatchId != null) {
//                                    whoCallRepeat = currentNode;
//                                    currentNode = graph.get(missMatchId);
//                                    return currentNode;
//                                }
//                            } else {
//                                currentNode = graph.get("transfer");
//                                return currentNode;
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            currentNode = whoCallRepeat;
//            return whoCallRepeat;
//        }
//        return currentNode;
//    }
}






























