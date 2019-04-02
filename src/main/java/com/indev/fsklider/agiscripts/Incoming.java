package com.indev.fsklider.agiscripts;

import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.GraphExecutor;
import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.Node;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.propeties.Property;
import com.indev.fsklider.utils.Utils;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class Incoming extends BaseAgiScript {


    public void service(AgiRequest request, AgiChannel channel) throws AgiException {
        try {
            answer();
            Node currentNode;
            Context context = new Context();
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
                    if (!context.getCommands().empty()) {
                        Stack<Command> commands = context.getCommands();
                        while (!commands.empty()) {
                            Command command = commands.pop();
                            System.out.println("Команда: " + command.getApp());
                            System.out.println("Опции: " + command.getOption());
                            exec(command.getApp(), command.getOption());
                            context.setRecogResult(getVariable("RECOG_RESULT"));
                        }
                        System.out.println(getVariable("RECOG_RESULT"));
                    }
                }
                context.setContextMap(new HashMap<>());
                hangup();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            exec("MRCPSynth","Единый call-центр\\, меня зовут Алена\\, чем могу помочь?");
//            exec("MRCPRecog", "c");
//            if (getComplex(getVariable("RECOG_RESULT"))) {
//                exec("MRCPSynth","Вы хотите проконсультировать с целью приобретения?");
//                exec("MRCPRecog", "/etc/asterisk/grammar.xml,f=beep&b=1&i=any");
//            } else {
//                exec("MRCPSynth","Ты похоже туповат");
//            }

        } catch (org.asteriskjava.fastagi.AgiHangupException e) {
            System.out.println("the user hanged up!!");
            setVariable("myvar", "the user hanged up!!");
        }
    }

    private boolean getComplex(String xml) {
        String message = Utils.getMessage(xml);
        for (String complex : Property.getEstate())
            if (message.contains(complex)) {
                return true;
            }
        return false;
    }

}
