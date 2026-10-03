package ru.nsu.oop.graph.model;

import java.util.List;

public interface Graph<T> {

    void addVertex(T vertex);

    void addEdge(T from, T to);

    List<T> getNeighbours(T vertex);

    List<T> getVertexesList();

    void deleteVertex(T vertex);

    void deleteEdge(T from, T to);
}
