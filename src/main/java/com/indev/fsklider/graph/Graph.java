package com.indev.fsklider.graph;
import java.util.*;

public class Graph {
    HashMap<String, List<String>> graph = new HashMap<>();

    public void addVertex(String vertexName) {
        if (!hasVertex(vertexName)) {
            graph.put(vertexName, new ArrayList<>());
        }
    }

    public boolean hasVertex(String vertexName) {
        return graph.containsKey(vertexName);
    }

    public boolean hasEdge(String vertexName1, String vertexName2) {
        if (!hasVertex(vertexName1)) return false;
        List<String> edges = graph.get(vertexName1);
        return Collections.binarySearch(edges, vertexName2) != -1;
    }

    public void addEdge(String vertexName1, String vertexName2) {
        if (!hasVertex(vertexName1)) addVertex(vertexName1);
        if (!hasVertex(vertexName2)) addVertex(vertexName2);
        List<String> edges1 = graph.get(vertexName1);
        edges1.add(vertexName2);
        Collections.sort(edges1);
    }

    public Map<String, List<String>> getVertexMap() {
        return graph;
    }

}

