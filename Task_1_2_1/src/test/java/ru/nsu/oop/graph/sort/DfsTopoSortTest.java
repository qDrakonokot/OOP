package ru.nsu.oop.graph.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.oop.graph.model.AdjacencyList;
import ru.nsu.oop.graph.model.Graph;

class DfsTopoSortTest {

    @Test
    void sort_validDAG_returnsCorrectTopologicalOrder() {
        // Arrange: Создаем DAG (Направленный ациклический граф)
        // A -> B, A -> C, B -> D, C -> D
        Graph<String> graph = new AdjacencyList<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        // Act
        List<String> sorted = DfsTopoSort.TopoSort(graph);

        // Assert
        assertEquals(4, sorted.size());
        // В топологической сортировке родитель всегда должен идти ДО своих детей.
        // Проверяем индексы элементов в итоговом списке.
        assertTrue(sorted.indexOf("A") < sorted.indexOf("B"));
        assertTrue(sorted.indexOf("A") < sorted.indexOf("C"));
        assertTrue(sorted.indexOf("B") < sorted.indexOf("D"));
        assertTrue(sorted.indexOf("C") < sorted.indexOf("D"));
    }

    @Test
    void sort_disconnectedGraph_sortsAllComponents() {
        // Arrange: Граф из двух независимых кусков: A->B и X->Y
        Graph<String> graph = new AdjacencyList<>();
        graph.addEdge("A", "B");
        graph.addEdge("X", "Y");

        // Act
        List<String> sorted = DfsTopoSort.TopoSort(graph);

        // Assert
        assertEquals(4, sorted.size());
        assertTrue(sorted.indexOf("A") < sorted.indexOf("B"));
        assertTrue(sorted.indexOf("X") < sorted.indexOf("Y"));
    }

    @Test
    void sort_graphWithCycle_throwsException() {
        // Arrange: Создаем цикл A -> B -> C -> A
        Graph<String> graph = new AdjacencyList<>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> DfsTopoSort.TopoSort(graph));
    }

    @Test
    void sort_emptyGraph_returnsEmptyList() {
        Graph<String> graph = new AdjacencyList<>();
        List<String> sorted = DfsTopoSort.TopoSort(graph);
        assertTrue(sorted.isEmpty());
    }
}