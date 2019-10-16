package com.indev.fsklider.graph.context;

import com.indev.fsklider.beans.ResponseEvent;
import com.indev.fsklider.graph.results.Command;
import com.indev.fsklider.graph.results.DialogResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Stack;

@Component
@Scope("prototype")
@Getter @Setter
public class Context {
    private HashMap<String, String> contextMap = new HashMap<>();

    private String recogResult;
    private DialogResult result = new DialogResult();
    private Stack<Command> commands = new Stack<>();
    private String nextId; //
        private String previousId;  //
    private ResponseEvent event = new ResponseEvent();
    private boolean isEnd;
    private String callerId;
}
