package ru.nsu.oop.graph.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
}