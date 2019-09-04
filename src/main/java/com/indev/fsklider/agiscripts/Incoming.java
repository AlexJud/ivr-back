package com.indev.fsklider.agiscripts;

import com.indev.fsklider.beans.EventType;
import com.indev.fsklider.beans.ResponseEvent;
import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.*;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.services.HttpHelper;
import com.indev.fsklider.services.SocketService;
import org.apache.log4j.Logger;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class Incoming extends BaseAgiScript {

    @Autowired
    SocketService socket;

    private HttpHelper http = new HttpHelper();
    private Context context = new Context();
    private static final Logger log = Logger.getLogger(Incoming.class);
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {
        try {
            answer();
            String callerId = getVariable("CALLERID(ANI)");
            context.getContextMap().put("callerId", callerId);
            context.setCallerId(callerId);
            log.info("Поступил звонок с номера " + callerId);
//            sendCallStart(callerId);
//            socket.sendSystemMessage("Звонок начался");
            Node currentNode;
            GraphBuilder builder = new GraphBuilder(System.getProperty("user.dir"));
            Map<String, Node> graph = builder.getGraph();
            context.setEnd(false);
            String nextId = "root";
            while (!context.isEnd()) {
                if (nextId == null) {
                    break;
                }
                currentNode = graph.get(nextId);
                currentNode.setContext(context);
                log.info("Выполняется " + currentNode.getClass().getSimpleName() + ": " + currentNode.getId());
                nextId = currentNode.run();
                log.info("Node: " + currentNode.getId() + " - завершил выполнение");
                context = currentNode.getContext();
//                if (currentNode instanceof ActionNode) {
//                    sendSystemSay(callerId, currentNode.getId());
//                }
//                if (currentNode instanceof ExtractNode) {
//                    sendAbonentSay(callerId);
//                } else if (currentNode instanceof TransferNode) {
//                    sendCallEnd(callerId);
//                }

                sendMessage(currentNode, callerId);
                if (!context.getCommands().empty()) {
                    Stack<Command> commands = context.getCommands();
                    while (!commands.empty()) {
                        Command command = commands.pop();
                        log.info("Команда: " + command.getApp());
                        log.info("Опции: " + command.getOption());
                        exec(command.getApp(), command.getOption());
                        context.setRecogResult(getVariable("RECOG_INPUT(0)"));
                        log.info("Результат распознавания: " + getVariable("RECOG_INPUT(0)"));
                    }
                }
            }
            context.setContextMap(new HashMap<>());
            hangup();
        } catch (org.asteriskjava.fastagi.AgiHangupException e) {
            System.out.println("<<<The user hanged up>>>");
            socket.sendSystemMessage("Звонок завершён");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        } else if (currentNode instanceof TransferNode) {
            socket.sendServerMessage(context.getEvent().getSystemText());

//          sendCallEnd(callerId);
        }
    }

    private void sendCallStart(String callerId) {
        ResponseEvent event = new ResponseEvent();
        event.setTimestamp(new Date().getTime());
        event.setType(EventType.CALL_START);
        event.setCallId(callerId);
        http.doPost(event);
    }

    private void sendSystemMessage(String callerId) {
        ResponseEvent event = new ResponseEvent();
        event.setTimestamp(new Date().getTime());
        event.setType(EventType.CALL_START);
        event.setCallId(callerId);
        http.doPost(event);
    }

    private void sendSystemSay(String callerId, String nodeId) throws AgiException {
        ResponseEvent event = context.getEvent();
        event.setDecision(nodeId);
        event.setTimestamp(new Date().getTime());
        event.setType(EventType.SYSTEM_SAY);
        event.setCallId(callerId);
        event.setTokenList(context.getContextMap());
        http.doPost(event);
    }

    private void sendAbonentSay(String callerId) throws AgiException {
        ResponseEvent event = context.getEvent();
        event.setTimestamp(new Date().getTime());
        event.setType(EventType.ABONENT_SAY);
        event.setTokenList(context.getContextMap());
        event.setCallId(callerId);
        String recog_result = getVariable("RECOG_INPUT(0)");
//        String recog_result = Utils.getMessage(getVariable("RECOG_INPUT(0)"));
        event.setAbonentText(recog_result);
        http.doPost(event);
    }

    private void sendCallEnd(String callerId) {
        ResponseEvent event = context.getEvent();
        event.setTimestamp(new Date().getTime());
        event.setType(EventType.CALL_END);
        event.setCallId(callerId);
        http.doPost(event);
    }
}
