package com.indev.fsklider.graph;

import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.Node;
import com.indev.fsklider.graph.results.DialogResult;

import java.util.ArrayList;
import java.util.Map;

public class GraphExecutor {
    private Map<String, Node> graph;
    private Node currentNode;
    private Context context;
    private Integer repeat = 0;
    private ArrayList<String> data;
    private State state = State.START;
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
        switch (state) {
            case START: {
                state = State.PARSE;
                return graph.get("root");
            }
            case PARSE: {
                startAllCheck();
            }
            case BUY: {
                return checkENS();
            }
            case SUPPORT: {
                return checkENS(); //TODO Проверка должна быть только на имя и ЖК
            }
            case TRANSFER: {
                return graph.get("ask_number");
            }
            case UNKNOWN: {
                return graph.get("send");
            }
            case ASK_ESTATE: {
                graph.get("check_estate").run();
            }
            case ASK_NAME: {
                graph.get("check_name").run();
            }
            case ASK_SOURCE: {
                graph.get("check_source").run();
            }
            case SEND: {
                return graph.get("send");
            }
        }

        if (context.getNextId() == null) {
            return graph.get("root");
        }
        if (context.getNextId().equals("end")) {

        }
        return graph.get(context.getNextId());
    }

    private void startAllCheck() {
        graph.get("check_buy").run();
        //Если после проверки на ключевые слова "покупки" поле reason не пустое, значит человек хочет купить
        if (context.getResult().getReason() != null) {
            state = State.BUY;
        } else {
            graph.get("check_support").run();
            if (context.getResult().getReason() != null) {
                state = State.SUPPORT;
            } else {
                graph.get("check_transfer").run();
                if (context.getResult().getReason() != null) {
                    state = State.TRANSFER;
                } else {
                    state = State.UNKNOWN;
                }
            }
        }
        graph.get("check_estate").run();
        graph.get("check_name").run();
        graph.get("check_source").run();
    }

    private Node checkENS() { //Estate, Name, Support
        if (context.getResult().getEstate() == null) {
            state = State.ASK_ESTATE;
            return graph.get("ask_estate");
        } else if (context.getResult().getName() == null) {
            state = State.ASK_NAME;
            return graph.get("ask_name");
        } else if (context.getResult().getSource() == null) {
            state = State.ASK_SOURCE;
            return graph.get("ask_source");
        } else  {
            state = State.SEND;
            return graph.get("send");
        }
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






























