package com.indev.fsklider.agiscripts;

import com.indev.fsklider.commands.SpeechAndHangup;
import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.*;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.models.Edge;
import com.indev.fsklider.services.SocketService;
import com.indev.fsklider.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.util.*;

@Service
@Scope("request")
public class Incoming extends BaseAgiScript {

    @Autowired
    @Getter
    SocketService socket;

    private Context context = new Context();
    private static final Logger log = Logger.getLogger(Incoming.class);
    @Getter
    private GraphBuilder builder = null;
    @Setter
    private boolean hangup;

//    private Map<String, ArrayList<Edge>> edgeMap = null;
//    private Map<String, Node> graph = null;
//    private HashMap<String, String> variableMap = null;

    public void service(AgiRequest request, AgiChannel channel) {
        try {
            Dialog currentNode;
            builder = new GraphBuilder(System.getProperty("user.dir"));
            builder.getGraph();

            this.hangup = false;
            answer();

            String callerId = getVariable("CALLERID(ANI)");
            log.info("Поступил звонок с номера " + callerId);

            context.getContextMap().put("callerId", callerId);
            context.setCallerId(callerId);

//       ---------------- construct Edges

            context.setEnd(false);
            String nextId = "root";

            int counterRepeat = 1;
            while (!context.isEnd()) {


                currentNode = builder.getNodeMap().get(nextId);
                socket.sendHighlightMessage(currentNode.getId());

//                if (currentNode instanceof Dialog) {


//                 ---------------   execute node processes -> ACTION
                log.info("TRACE Executable class" + currentNode);
                currentNode.run(this);

                if (hangup){
                    context.setEnd(true);
                    break;
                }

//                    --------------  start to choose next node
                ArrayList<Edge> edges = builder.getEdgeMap().get(currentNode.getId());

//                       ------------------------------ temp
//                if (result == null || edges == null || edges.size() == 0) {
//                    log.warn("TRACE 1");
//                    context.setEnd(true);
//                    break;
//                        --------------------------------temp
//                }
                if (edges.size() == 1 && edges.get(0).getKeyWords().size() == 0) {
                    log.warn("Переход по единственной безусловной связи");
                    nextId = edges.get(0).getTargetId();
                    edges.get(0).setMatchWord(currentNode.getResultAnswer());
                    continue;
                }

//                --------------- equals words
                log.info("choose next Edge ->");
                ArrayList<Edge> matchList = new ArrayList<>();
                String result = currentNode.getResultAnswer().toLowerCase();
                List sourceList = Arrays.asList(result.split(" "));

                edges.forEach(edge -> {
                    edge.getKeyWords().forEach(word -> {
                        word = word.toLowerCase();
                        if (sourceList.contains(word)) {
                            edge.setMatchWord(word);
                            matchList.add(edge);
                        }
                    });
                });

//                        -------------- choose next if
                if (matchList.size() > 1) {
                    matchList.forEach(rec -> log.info("---> Many matches! Edges " + rec));
                    nextId = matchList.get(0).getTargetId();
                } else if (matchList.size() == 0) {
                    log.info("No match ------------------------> Again");
                    Edge errorPath = getErrorPath(currentNode, builder.getEdgeMap());
                    if (errorPath != null) {
                        log.info("Переход по связи 'Ошибка'");
                        nextId = errorPath.getTargetId();
                    } else {

                        if (counterRepeat++ < 3) {
                            log.info("Повторение текущего диалога");
                            nextId = currentNode.getId();
                        } else {
                            context.setEnd(true);
                            log.info("Ответ не распознан 3 раза, вешаю трубку");
                            SpeechAndHangup.errorRecognize(this);
                        }
                    }

                } else {
                    log.warn("Переход по распознанной связи");
                    nextId = matchList.get(0).getTargetId();
                }


//                } else {
//
//                    currentNode.setContext(context);
//                    nextId = currentNode.run();
//                    if (nextId == null) {
//                        log.info("Нет вариантов для перехода (next Node ID == null). Завершение звонка.");
//                        break;
//                    }
//                    context = currentNode.getContext();
//
//                    sendMessage(currentNode, callerId);
//
//
//                    if (!context.getCommands().empty()) {
//                        Stack<Command> commands = context.getCommands();
//                        while (!commands.empty()) {
//                            Command command = commands.pop();
//                            log.info("Команда: " + command.getApp());
//                            log.info("Опции: " + command.getOption());
//
//                            if (currentNode instanceof Executable) {
//                                System.out.println("DSFSDFSDF");
//                            }
//
//                            exec(command.getApp(), command.getOption());
//
//                            String answer = getVariable("RECOG_INPUT(0)");
//                            if (answer != null) {
//                                context.setRecogResult(answer.toLowerCase());
//                            } else {
//                                context.setRecogResult("");
//                            }
//                            log.info("Результат распознавания: " + answer);
//                        }
//                    }
//                }
            }
            context.setContextMap(new HashMap<>());
            hangup();
        } catch (org.asteriskjava.fastagi.AgiHangupException e) {
            System.out.println("<<<The user hanged up>>>");
            socket.sendSystemMessage("Звонок завершён");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Edge getErrorPath(Dialog node, Map<String, ArrayList<Edge>> map) {
        ArrayList<Edge> edges = map.get(node.getId());
        ArrayList<Edge> finded = new ArrayList<>();
        edges.forEach(edge -> {
            if (edge.getKeyWords().size() == 0) {
                finded.add(edge);
            }
        });
        if (finded.size() > 1) {
            log.warn("Found more than 1 unmarked links from Node " + node.getId());
        }
        return finded.size() == 0 ? null : finded.get(0);
    }

    private void sendMessage(Node currentNode, String callerId) {
        if (currentNode instanceof ActionNode) {
            socket.sendServerMessage(context.getEvent().getSystemText());
//            sendSystemSay(callerId, currentNode.getId());
        }
        if (currentNode instanceof ClassifierNode) {
            socket.sendSystemMessage("Выбрана ветка со словом " + context.getContextMap().get("reason"));
        }
        if (currentNode instanceof ExtractNode) {
            String varName = ((ExtractNode) currentNode).getProps().getVarName();
            String rawVarName = ((ExtractNode) currentNode).getProps().getRawVarName();
            String value = context.getContextMap().get(varName);
            String rawValue = context.getContextMap().get(rawVarName);
            if (value == null && rawValue == null) {
                socket.sendSystemMessage(varName + " = " + "Нет совпадений. Повтор.");
            } else if (value != null) {
                socket.sendSystemMessage("Сохранено " + varName + " = " + context.getContextMap().get(varName));
            } else {
                socket.sendSystemMessage("Сохранено " + rawVarName + " = " + context.getContextMap().get(rawVarName));
            }
            socket.sendUserMessage(callerId, currentNode.getContext().getRecogResult());
//            sendAbonentSay(callerId);
        } else if (currentNode instanceof EndNode) {
            socket.sendServerMessage(context.getEvent().getSystemText());

//          sendCallEnd(callerId);
        }


    }

//    private void sendCallStart(String callerId) {
//        ResponseEvent event = new ResponseEvent();
//        event.setTimestamp(new Date().getTime());
//        event.setType(EventType.CALL_START);
//        event.setCallId(callerId);
//        http.doPost(event);
//    }
//
//    private void sendSystemMessage(String callerId) {
//        ResponseEvent event = new ResponseEvent();
//        event.setTimestamp(new Date().getTime());
//        event.setType(EventType.CALL_START);
//        event.setCallId(callerId);
//        http.doPost(event);
//    }
//
//    private void sendSystemSay(String callerId, String nodeId) throws AgiException {
//        ResponseEvent event = context.getEvent();
//        event.setDecision(nodeId);
//        event.setTimestamp(new Date().getTime());
//        event.setType(EventType.SYSTEM_SAY);
//        event.setCallId(callerId);
//        event.setTokenList(context.getContextMap());
//        http.doPost(event);
//    }
//
//    private void sendAbonentSay(String callerId) throws AgiException {
//        ResponseEvent event = context.getEvent();
//        event.setTimestamp(new Date().getTime());
//        event.setType(EventType.ABONENT_SAY);
//        event.setTokenList(context.getContextMap());
//        event.setCallId(callerId);
//        String recog_result = getVariable("RECOG_INPUT(0)");
////        String recog_result = Utils.getMessage(getVariable("RECOG_INPUT(0)"));
//        event.setAbonentText(recog_result);
//        http.doPost(event);
//    }
//
//    private void sendCallEnd(String callerId) {
//        ResponseEvent event = context.getEvent();
//        event.setTimestamp(new Date().getTime());
//        event.setType(EventType.CALL_END);
//        event.setCallId(callerId);
//        http.doPost(event);
//    }
}
