package ru.nsu.oop.graph.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.oop.graph.model.AdjacencyList;
import ru.nsu.oop.graph.model.Graph;

class GraphReaderTest {

    // JUnit автоматически создаст временную директорию для тестов
    @TempDir
    Path tempDir;

    @Test
    void populateGraph_validFile_populatesGraphCorrectly() throws IOException {
        // Arrange: Создаем временный файл с правильным форматом
        Path tempFile = tempDir.resolve("valid_graph.txt");
        List<String> lines = Arrays.asList(
            "A B",   // Ребро A -> B
            "B C",   // Ребро B -> C
            "D",     // Изолированная вершина D
            "  "     // Пустая строка (должна игнорироваться)
        );
        Files.write(tempFile, lines);

        Graph<String> graph = new AdjacencyList<>();

        // Act: Читаем файл, используя лямбду (s -> s) для парсинга строк
        GraphReader.populateGraph(tempFile.toString(), graph, s -> s);

        // Assert
        assertEquals(4, graph.getVertexesList().size());
        assertTrue(graph.getVertexesList().containsAll(Arrays.asList("A", "B", "C", "D")));

        assertTrue(graph.getNeighbours("A").contains("B"));
        assertTrue(graph.getNeighbours("B").contains("C"));
        assertTrue(graph.getNeighbours("D").isEmpty());
    }

    @Test
    void populateGraph_invalidFormat_throwsException() throws IOException {
        // Arrange: Создаем файл с кривым форматом (3 вершины в строке)
        Path tempFile = tempDir.resolve("invalid_graph.txt");
        Files.write(tempFile, List.of("A B C"));

        Graph<String> graph = new AdjacencyList<>();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> GraphReader.populateGraph(tempFile.toString(), graph, s -> s));
    }

    @Test
    void populateGraph_fileNotFound_throwsRuntimeException() {
        Graph<String> graph = new AdjacencyList<>();

        // Пытаемся прочитать несуществующий файл
        String fakePath = tempDir.resolve("ghost.txt").toString();

        assertThrows(RuntimeException.class,
            () -> GraphReader.populateGraph(fakePath, graph, s -> s));
    }

    @Test
    void populateGraph_withIntegerParser_parsesNumbersCorrectly() throws IOException {
        // Arrange: Файл с числами
        Path tempFile = tempDir.resolve("numbers.txt");
        Files.write(tempFile, Arrays.asList("1 2", "2 3"));

        Graph<Integer> graph = new AdjacencyList<>();

        // Act: Передаем Integer::parseInt как парсер
        GraphReader.populateGraph(tempFile.toString(), graph, Integer::parseInt);

        // Assert
        assertTrue(graph.getVertexesList().contains(1));
        assertTrue(graph.getNeighbours(1).contains(2));
    }
}