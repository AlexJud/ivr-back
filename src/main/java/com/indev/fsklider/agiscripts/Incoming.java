package com.indev.fsklider.agiscripts;

import com.indev.fsklider.beans.EventType;
import com.indev.fsklider.beans.ResponseEvent;
import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.ActionNode;
import com.indev.fsklider.graph.nodes.ExtractNode;
import com.indev.fsklider.graph.nodes.Node;
import com.indev.fsklider.graph.nodes.TransferNode;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.services.HttpHelper;
import com.indev.fsklider.utils.Utils;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class Incoming extends BaseAgiScript {

    private HttpHelper http = new HttpHelper();
    private Context context = new Context();
    public void service(AgiRequest request, AgiChannel channel) throws AgiException {
        try {
            answer();
            String callerId = getVariable("CALLERID(ANI)");
            sendCallStart(callerId);
            Node currentNode;
            try {
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
                    nextId = currentNode.run();
                    context = currentNode.getContext();
                    if (currentNode instanceof ActionNode) {
                        sendSystemSay(callerId, currentNode.getId());
                    }
                    if (currentNode instanceof ExtractNode) {
                        sendAbonentSay(callerId);
                    } else if (currentNode instanceof TransferNode) {
                        sendCallEnd(callerId);
                    }
                    if (!context.getCommands().empty()) {
                        Stack<Command> commands = context.getCommands();
                        while (!commands.empty()) {
                            Command command = commands.pop();
                            System.out.println("Команда: " + command.getApp());
                            System.out.println("Опции: " + command.getOption());
                            exec(command.getApp(), command.getOption());
                            context.setRecogResult(getVariable("RECOG_INPUT(0)"));
                        }
                    }
                }
                context.setContextMap(new HashMap<>());
                hangup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (org.asteriskjava.fastagi.AgiHangupException e) {
            System.out.println("<<<The user hanged up>>>");
        }
    }

    private void sendCallStart(String callerId) {
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
