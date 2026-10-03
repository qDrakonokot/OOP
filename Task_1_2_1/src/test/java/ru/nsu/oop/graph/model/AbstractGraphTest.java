package ru.nsu.oop.graph.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AbstractGraphTest {

    @Test
    void equals_differentImplementationsWithSameData_returnsTrue() {
        // Arrange: Создаем два РАЗНЫХ типа графа
        Graph<String> listGraph = new AdjacencyList<>();
        Graph<String> matrixGraph = new IncidenceMatrix<>();

        // Заполняем их ОДИНАКОВЫМИ данными
        listGraph.addEdge("A", "B");
        listGraph.addEdge("B", "C");

        matrixGraph.addEdge("A", "B");
        matrixGraph.addEdge("B", "C");

        // Act & Assert: Они должны быть равны логически
        assertEquals(listGraph, matrixGraph);
        assertEquals(matrixGraph, listGraph);

        // Их хеш-коды тоже обязаны совпадать
        assertEquals(listGraph.hashCode(), matrixGraph.hashCode());
    }

    @Test
    void equals_sameImplementationsWithDifferentData_returnsFalse() {
        Graph<String> graph1 = new AdjacencyList<>();
        graph1.addEdge("A", "B");

        Graph<String> graph2 = new AdjacencyList<>();
        graph2.addEdge("A", "C"); // Другое ребро

        assertNotEquals(graph1, graph2);
    }

    @Test
    void hashCode_equalGraphs_haveSameHashCode() {
        // Arrange: Два разных графа с одинаковыми данными
        Graph<String> listGraph = new AdjacencyList<>();
        listGraph.addEdge("A", "B");
        listGraph.addVertex("C");

        Graph<String> matrixGraph = new AdjacencyMatrix<>();
        matrixGraph.addEdge("A", "B");
        matrixGraph.addVertex("C");

        // Act & Assert: По контракту Java, если equals == true, то и hashCode обязаны быть равны
        assertEquals(listGraph, matrixGraph);
        assertEquals(listGraph.hashCode(), matrixGraph.hashCode());
    }

    @Test
    void hashCode_differentInsertionOrder_haveSameHashCode() {
        // Arrange: Графы заполняются в разном порядке
        Graph<String> graph1 = new AdjacencyList<>();
        graph1.addEdge("A", "B");
        graph1.addEdge("C", "D");

        Graph<String> graph2 = new AdjacencyList<>();
        graph2.addEdge("C", "D");
        graph2.addEdge("A", "B");

        // Act & Assert: Хеш-код графа (как множества) не должен зависеть от порядка добавления
        assertEquals(graph1.hashCode(), graph2.hashCode());
    }

    @Test
    void toString_emptyGraph_returnsEmptyBrackets() {
        // Arrange
        Graph<String> emptyGraph = new AdjacencyList<>();

        // Act
        String result = emptyGraph.toString();

        // Assert
        assertEquals("[]", result);
    }

    @Test
    void toString_graphWithEdges_returnsFormattedString() {
        // Arrange
        Graph<String> graph = new AdjacencyList<>();
        graph.addEdge("A", "B");
        graph.addVertex("C");

        // Act
        String result = graph.toString();

        // Assert
        // Так как порядок ключей в Map не гарантирован, мы проверяем наличие нужных блоков
        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
        assertTrue(result.contains("A -> [B]"));
        assertTrue(result.contains("B -> []"));
        assertTrue(result.contains("C -> []"));
    }
}