package ru.nsu.oop.graph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация направленного графа на основе списка смежности (Adjacency List). Оптимальна для
 * разреженных графов и частых операций добавления/удаления вершин.
 *
 * @param <T> тип данных вершин
 */
public class AdjacencyList<T> extends AbstractGraph<T> {

    private final Map<T, List<T>> adjList;

    public AdjacencyList() {
        adjList = new HashMap<>();
    }

    @Override
    public void addVertex(T vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void addEdge(T from, T to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("Self-loops forbidden");
        }

        addVertex(from);
        addVertex(to);

        List<T> neighbours = adjList.get(from);
        if (!neighbours.contains(to)) {
            neighbours.add(to);
        }
    }

    @Override
    public List<T> getNeighbours(T vertex) {
        List<T> neighbours = adjList.getOrDefault(vertex, Collections.emptyList());

        return Collections.unmodifiableList(neighbours);
    }

    @Override

    public List<T> getVertexesList() {
        return new ArrayList<>(adjList.keySet());
    }

    @Override
    public void deleteEdge(T from, T to) {
        if (adjList.containsKey(from)) {
            adjList.get(from).remove(to);
        }
    }

    @Override
    public void deleteVertex(T vertex) {
        if (!adjList.containsKey(vertex)) {
            return;
        }
        adjList.remove(vertex);

        for (List<T> neighbours : adjList.values()) {
            neighbours.remove(vertex);
        }
    }
}
