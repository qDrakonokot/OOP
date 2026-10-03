package ru.nsu.oop.graph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация направленного графа на основе матрицы смежности (Adjacency Matrix). Использует паттерн
 * Index Mapping для связи объектов типа T с индексами двумерного массива. Оптимальна для плотных
 * графов и быстрой проверки наличия ребра за O(1).
 *
 * @param <T> тип данных вершин
 */
public class AdjacencyMatrix<T> extends AbstractGraph<T> {

    private final List<List<Boolean>> adjMatrix;
    private final Map<T, Integer> vertexToIndex;
    private final List<T> indexToVertex;

    public AdjacencyMatrix() {
        this.adjMatrix = new ArrayList<>();
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new ArrayList<>();
    }

    @Override
    public void addVertex(T vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            int newIndex = indexToVertex.size();
            indexToVertex.add(vertex);
            vertexToIndex.put(vertex, newIndex);

            for (List<Boolean> row : adjMatrix) {
                row.add(false);
            }

            List<Boolean> newRow = new ArrayList<>(Collections.nCopies(newIndex + 1, false));
            adjMatrix.add(newRow);
        }
    }

    @Override
    public void addEdge(T from, T to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("Self-loops forbidden");
        }

        addVertex(from);
        addVertex(to);

        int fromIndex = vertexToIndex.get(from);
        int toIndex = vertexToIndex.get(to);

        adjMatrix.get(fromIndex).set(toIndex, true);
    }

    @Override
    public List<T> getNeighbours(T vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            return Collections.emptyList();
        }

        List<T> neighbours = new ArrayList<>();

        List<Boolean> row = adjMatrix.get(vertexToIndex.get(vertex));
        for (int i = 0; i < row.size(); ++i) {
            if (row.get(i)) {
                neighbours.add(indexToVertex.get(i));
            }
        }

        return Collections.unmodifiableList(neighbours);
    }

    @Override
    public List<T> getVertexesList() {
        return Collections.unmodifiableList(indexToVertex);
    }

    @Override
    public void deleteEdge(T from, T to) {
        Integer fromIndex = vertexToIndex.get(from);
        Integer toIndex = vertexToIndex.get(to);

        if (fromIndex != null && toIndex != null) {
            adjMatrix.get(fromIndex).set(toIndex, false);
        }
    }

    @Override
    public void deleteVertex(T vertex) {
        Integer indexToRemove = vertexToIndex.get(vertex);
        if (indexToRemove == null) {
            return;
        }

        adjMatrix.remove((int) indexToRemove);

        for (List<Boolean> row : adjMatrix) {
            row.remove((int) indexToRemove);
        }

        indexToVertex.remove((int) indexToRemove);
        vertexToIndex.remove(vertex);

        for (int i = indexToRemove; i < indexToVertex.size(); i++) {
            vertexToIndex.put(indexToVertex.get(i), i);
        }
    }
}
