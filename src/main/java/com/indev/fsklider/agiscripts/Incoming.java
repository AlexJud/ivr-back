package com.indev.fsklider.agiscripts;

import com.indev.fsklider.commands.SpeechAndHangup;
import com.indev.fsklider.handlers.excepts.HangUpException;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.models.Edge;
import com.indev.fsklider.models.enums.MessageType;
import com.indev.fsklider.services.GraphBuilderService;
import com.indev.fsklider.services.SocketService;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.asteriskjava.fastagi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Log4j
public class Incoming extends BaseAgiScript {

    @Autowired
    @Getter
    private SocketService socket;

    public void service(AgiRequest request, AgiChannel channel) {

        GraphBuilderService builder = null;
        boolean hangup = true;
        String sessionId = null;
        Dialog currentNode;
        String nextId = "root";
        int counterRepeat = 1;

        try {
            sessionId = getVariable("EXTEN");

        } catch (AgiException e) {
            e.printStackTrace();
            sessionId = "errorId";
        }


        try {
            String callerId = getVariable("CALLERID(ANI)");
            log.info("Поступил звонок с номера " + callerId);

            builder = new GraphBuilderService(sessionId);
            builder.getGraph();

//            hangup = false;
            answer();

//       ---------------- construct Edges


//            while (!context.isEnd()) {
            while (hangup) {
                log.info("STATUS CHANNEL " + channel.getChannelStatus());

                currentNode = builder.getNodeMap().get(nextId);
                socket.sendMessage(currentNode.getId(), MessageType.HIGHLIGHT, sessionId);


//                 ---------------   execute node processes -> ACTION
                log.info("Выполнение комманд текущего узла: " + currentNode);
                currentNode.run(this, builder);

//                    --------------  start to choose next node
                ArrayList<Edge> edges = builder.getEdgeMap().get(currentNode.getId());


//             ---->   0) если нет связи
                if (edges.size() == 0) {
                    break;
                }
//             ---->   1) одна безусловная связь
                if (edges.size() == 1 && edges.get(0).getKeyWords().size() == 0) {
                    log.trace("Переход по единственной безусловной связи");
                    nextId = edges.get(0).getTargetId();
                    edges.get(0).setMatchWord(currentNode.getResultAnswer());
                    continue;
                }

//                --------------- equals words
                log.trace("Выбор следующего узла по ответу собеседника ->");
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
                    matchList.forEach(rec -> log.info("Найдены повторящиеся ключевые слова у связей: " + rec));
                    nextId = matchList.get(0).getTargetId();
                } else if (matchList.size() == 0) {
                    log.info("No match ------------------------> Again");
                    Edge errorPath = getErrorPath(currentNode, builder.getEdgeMap());
                    if (errorPath != null) {
                        log.info("Переход по связи 'Ошибка'");
                        nextId = errorPath.getTargetId();
                    } else {

                        if (counterRepeat < 3) {
                            log.info("Повторение текущего диалога");
                            nextId = currentNode.getId();
                        } else {
//                            context.setEnd(true);
                            log.info("Ответ не распознан 3 раза, вешаю трубку");
                            SpeechAndHangup.errorRecognize(this);
                        }
                    }

                } else {
                    log.warn("Переход по распознанной связи");
                    nextId = matchList.get(0).getTargetId();
                }

                counterRepeat = nextId.equals(currentNode.getId()) ? ++counterRepeat : 1;
            }
//            context.setContextMap(new HashMap<>());
        } catch (AgiHangupException | HangUpException e) {
            System.out.println();
            log.info("<<< Собеседник повесил трубку >>>");
            socket.sendMessage("Звонок завершён", MessageType.SYSTEM, sessionId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                hangup();
//            } catch (AgiException e) {
//                e.printStackTrace();
//            }
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

//    private void sendMessage(Node currentNode, String callerId) {
//        if (currentNode instanceof ActionNode) {
//            socket.sendServerMessage(context.getEvent().getSystemText());
//        }
//        if (currentNode instanceof ClassifierNode) {
//            socket.sendSystemMessage("Выбрана ветка со словом " + context.getContextMap().get("reason"));
//        }
//        if (currentNode instanceof ExtractNode) {
//            String varName = ((ExtractNode) currentNode).getProps().getVarName();
//            String rawVarName = ((ExtractNode) currentNode).getProps().getRawVarName();
//            String value = context.getContextMap().get(varName);
//            String rawValue = context.getContextMap().get(rawVarName);
//            if (value == null && rawValue == null) {
//                socket.sendSystemMessage(varName + " = " + "Нет совпадений. Повтор.");
//            } else if (value != null) {
//                socket.sendSystemMessage("Сохранено " + varName + " = " + context.getContextMap().get(varName));
//            } else {
//                socket.sendSystemMessage("Сохранено " + rawVarName + " = " + context.getContextMap().get(rawVarName));
//            }
//            socket.sendUserMessage(callerId, currentNode.getContext().getRecogResult());
//        } else if (currentNode instanceof EndNode) {
//            socket.sendServerMessage(context.getEvent().getSystemText());
//
//        }
//
//
//    }

}
