package ru.nsu.oop.graph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация направленного графа на основе матрицы инцидентности (Incidence Matrix). Строки матрицы
 * представляют вершины, а столбцы — направленные ребра. Связи обозначаются как -1 (исходящее ребро)
 * и 1 (входящее ребро).
 *
 * @param <T> тип данных вершин
 */
public class IncidenceMatrix<T> extends AbstractGraph<T> {

    private final List<List<Integer>> incMatrix;
    private final Map<T, Integer> vertexToIndex;
    private final List<T> indexToVertex;
    private int edgesCnt = 0;

    public IncidenceMatrix() {
        this.incMatrix = new ArrayList<>();
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new ArrayList<>();
    }

    @Override
    public void addVertex(T vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            int newIndex = indexToVertex.size();
            indexToVertex.add(vertex);
            vertexToIndex.put(vertex, newIndex);

            List<Integer> newRow = new ArrayList<>(Collections.nCopies(edgesCnt, 0));
            incMatrix.add(newRow);
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

        List<Integer> fromEdges = incMatrix.get(fromIndex);
        List<Integer> toEdges = incMatrix.get(toIndex);

        boolean hasEdge = false;
        for (int i = 0; i < edgesCnt; ++i) {
            if (fromEdges.get(i).equals(-1) && toEdges.get(i).equals(1)) {
                hasEdge = true;
                break;
            }
        }

        if (!hasEdge) {
            for (List<Integer> row : incMatrix) {
                row.add(0);
            }

            incMatrix.get(fromIndex).set(edgesCnt, -1);
            incMatrix.get(toIndex).set(edgesCnt, 1);
            edgesCnt++;
        }
    }

    @Override
    public List<T> getNeighbours(T vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            return Collections.emptyList();
        }

        int vertexIndex = vertexToIndex.get(vertex);
        List<Integer> fromRow = incMatrix.get(vertexIndex);
        List<T> neighbours = new ArrayList<>();
        for (int i = 0; i < edgesCnt; ++i) {
            if (fromRow.get(i).equals(-1)) {
                for (int j = 0; j < incMatrix.size(); ++j) {
                    List<Integer> toRow = incMatrix.get(j);
                    if (toRow.get(i).equals(1)) {
                        neighbours.add(indexToVertex.get(j));
                        break;
                    }
                }
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

        if (fromIndex == null || toIndex == null) {
            return;
        }

        List<Integer> fromRow = incMatrix.get(fromIndex);
        List<Integer> toRow = incMatrix.get(toIndex);

        for (int edgeIndex = 0; edgeIndex < edgesCnt; edgeIndex++) {
            if (fromRow.get(edgeIndex).equals(-1) && toRow.get(edgeIndex).equals(1)) {
                for (List<Integer> row : incMatrix) {
                    row.remove(edgeIndex);
                }
                edgesCnt--;
                break;
            }
        }
    }

    @Override
    public void deleteVertex(T vertex) {
        Integer vIndex = vertexToIndex.get(vertex);
        if (vIndex == null) {
            return;
        }

        List<Integer> targetRow = incMatrix.get(vIndex);
        for (int edgeIndex = edgesCnt - 1; edgeIndex >= 0; edgeIndex--) {
            if (!targetRow.get(edgeIndex).equals(0)) {
                for (List<Integer> row : incMatrix) {
                    row.remove(edgeIndex);
                }
                edgesCnt--;
            }
        }

        incMatrix.remove((int) vIndex);
        indexToVertex.remove((int) vIndex);
        vertexToIndex.remove(vertex);

        for (int i = vIndex; i < indexToVertex.size(); i++) {
            vertexToIndex.put(indexToVertex.get(i), i);
        }
    }
}
