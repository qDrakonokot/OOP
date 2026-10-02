package ru.nsu.oop.graph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyList<T> extends AbstractGraph<T> {

    private final Map<T, List<T>> adjacencyList = new HashMap<>();

    public AdjacencyList(Map<T, List<T>> map) {

    }

    @Override
    public void addVertex(T vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<T>());
    }

    @Override
    public void addEdge(T from, T to) {
        addVertex(from);
        addVertex(to);

        adjacencyList.get(from).add(to);
    }

    @Override
    public List<T> getNeighbours(T vertex) {
        List<T> neighbours = adjacencyList.getOrDefault(vertex, Collections.emptyList());

        return Collections.unmodifiableList(neighbours);
    }

    @Override
    public List<T> getVertexesList() {
        return new ArrayList<>(adjacencyList.keySet());
    }


}
