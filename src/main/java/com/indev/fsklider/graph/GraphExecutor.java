package com.indev.fsklider.graph;

import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GraphExecutor {

    @Autowired
    Context context;

    private Map<String, Node> graph;
    private Node currentNode = null;

    @Autowired
    public GraphExecutor(GraphBuilder graphBuilder) {
        try {
            this.graph = graphBuilder.getGraph();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        String nextId = "root";
        while (!nextId.equals("end")) {
            currentNode = graph.get(nextId);
            currentNode.setContext(context);
            nextId = currentNode.run();
            context = currentNode.getContext();
        }
    }

    public Node getNext() {
        String nextId = "root";
        if (currentNode == null) {
            return graph.get("root");
        }
        if (context.getNextId().equals("end")) {

        }
        return graph.get(context.getNextId());
    }
}






























