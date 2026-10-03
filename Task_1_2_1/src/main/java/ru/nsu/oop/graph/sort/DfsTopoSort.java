package ru.nsu.oop.graph.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.oop.graph.model.Graph;

/**
 * Утилитный класс, предоставляющий алгоритмы сортировки графов.
 */
public final class DfsTopoSort {

    private DfsTopoSort() {
    }

    /**
     * Выполняет топологическую сортировку направленного ациклического графа (DAG) с использованием
     * алгоритма поиска в глубину (DFS).
     *
     * @param graph граф для сортировки (любая реализация интерфейса {@link Graph})
     * @param <T>   тип вершин графа
     * @return список вершин, отсортированных в топологическом порядке
     * @throws IllegalStateException если в графе обнаружен цикл (сортировка невозможна)
     */
    public static <T> List<T> TopoSort(Graph<T> graph) {
        Map<T, State> states = new HashMap<>();
        List<T> result = new ArrayList<>();

        for (T vertex : graph.getVertexesList()) {
            if (!states.containsKey(vertex)) {
                dfs(graph, vertex, states, result);
            }
        }

        Collections.reverse(result);
        return result;
    }

    private static <T> void dfs(Graph<T> graph, T vertex, Map<T, State> states, List<T> result) {
        states.put(vertex, State.GRAY);

        List<T> neighbours = graph.getNeighbours(vertex);

        for (T neighbour : neighbours) {
            if (states.containsKey(neighbour)) {
                if (states.get(neighbour).equals(State.GRAY)) {
                    throw new IllegalStateException("Graph contains a cycle!");
                }
            } else {
                dfs(graph, neighbour, states, result);
            }
        }

        states.put(vertex, State.BLACK);

        result.add(vertex);
    }

    private enum State {
        GRAY, // в процессе
        BLACK // обработан
    }
}
