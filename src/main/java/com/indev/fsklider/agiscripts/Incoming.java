package com.indev.fsklider.agiscripts;

import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.GraphExecutor;
import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.CallTransferNode;
import com.indev.fsklider.graph.nodes.Node;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.propeties.Property;
import com.indev.fsklider.utils.Utils;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class Incoming extends BaseAgiScript {

    public void service(AgiRequest request, AgiChannel channel) throws AgiException {
        try {
            Context context = new Context();
            answer();
            GraphBuilder builder = new GraphBuilder(System.getProperty("user.dir"));
            try {
                GraphExecutor executor = new GraphExecutor(builder.getGraph());
                context.setEnd(false);
                while (!context.isEnd()) {
                    executor.setContext(context);
                    Node currentNode = executor.getNext();
                    currentNode.setContext(context);
                    System.out.println("context: " + currentNode.getContext().getMatchResult());
                    System.out.println("context: " + currentNode.getContext().getRecogResult());
                    context = currentNode.run();
//                    if (context.getCommands() != null) {
                    Stack<Command> commands = context.getCommands();
                    while (!commands.empty()) {
                        Command command = commands.pop();
                        System.out.println("Команда: " + command.getApp());
                        System.out.println("Опции: " + command.getOption());
                        exec(command.getApp(), command.getOption());
                        context.setRecogResult(getVariable("RECOG_RESULT"));
                    }
                }
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
