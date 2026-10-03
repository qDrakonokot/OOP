package ru.nsu.oop.graph.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Абстрактный класс. JUnit не будет запускать его напрямую.
public abstract class GraphTestBase {

    protected Graph<String> graph;

    // Фабричный метод, который реализуют наследники
    protected abstract Graph<String> createGraph();

    @BeforeEach
    void setUp() {
        graph = createGraph();
    }

    @Test
    void addVertex_newVertex_addsSuccessfully() {
        graph.addVertex("A");
        assertTrue(graph.getVertexesList().contains("A"));
        assertEquals(1, graph.getVertexesList().size());
    }

    @Test
    void addEdge_validEdge_addsVerticesAndEdge() {
        graph.addEdge("A", "B");

        assertTrue(graph.getVertexesList().contains("A"));
        assertTrue(graph.getVertexesList().contains("B"));

        List<String> neighborsOfA = graph.getNeighbours("A");
        assertTrue(neighborsOfA.contains("B"));
        assertEquals(1, neighborsOfA.size());
    }

    @Test
    void addEdge_selfLoop_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "A"));
    }

    @Test
    void getNeighbours_nonExistingVertex_returnsEmptyList() {
        List<String> neighbors = graph.getNeighbours("Ghost");
        assertNotNull(neighbors);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    void getNeighbours_returnsUnmodifiableList() {
        graph.addEdge("A", "B");
        List<String> neighbors = graph.getNeighbours("A");

        // Проверяем инкапсуляцию: попытка изменить список должна выбросить ошибку
        assertThrows(UnsupportedOperationException.class, () -> neighbors.add("C"));
    }

    @Test
    void deleteEdge_existingEdge_removesOnlyThatEdge() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        graph.deleteEdge("A", "B");

        List<String> neighbors = graph.getNeighbours("A");
        assertFalse(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
    }

    @Test
    void deleteVertex_existingVertex_removesVertexAndAllConnectedEdges() {
        // Создаем сложную структуру: C -> B, A -> B, B -> D
        graph.addEdge("C", "B");
        graph.addEdge("A", "B");
        graph.addEdge("B", "D");

        // Удаляем центральную вершину
        graph.deleteVertex("B");

        // 1. Сама вершина исчезла
        assertFalse(graph.getVertexesList().contains("B"));

        // 2. Исходящие ребра исчезли (B -> D)
        assertTrue(graph.getNeighbours("B").isEmpty());

        // 3. Входящие ребра исчезли (C -> B и A -> B)
        assertFalse(graph.getNeighbours("C").contains("B"));
        assertFalse(graph.getNeighbours("A").contains("B"));

        // 4. Остальные вершины не пострадали
        assertTrue(graph.getVertexesList().contains("A"));
        assertTrue(graph.getVertexesList().contains("C"));
        assertTrue(graph.getVertexesList().contains("D"));
    }
}