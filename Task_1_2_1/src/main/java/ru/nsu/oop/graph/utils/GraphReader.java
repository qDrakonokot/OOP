package ru.nsu.oop.graph.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import ru.nsu.oop.graph.model.Graph;

/**
 * Утилитный класс для чтения структуры графа из текстовых файлов.
 */
public final class GraphReader {

    private GraphReader() {
    }

    /**
     * Читает граф из файла и заполняет переданный объект графа. Формат файла: каждая строка
     * содержит либо одну вершину, либо две вершины через пробел (ребро).
     *
     * @param filepath путь к файлу
     * @param graph    пустой граф, который нужно заполнить
     * @param parser   функция для преобразования строки из файла в тип вершины T
     */
    public static <T> void populateGraph(String filepath, Graph<T> graph,
        Function<String, T> parser) {
        File file = new File(filepath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                List<String> tokens = List.of(line.split("\\s+"));

                if (tokens.size() == 1) {
                    T vertex = parser.apply(tokens.get(0));
                    graph.addVertex(vertex);
                } else if (tokens.size() == 2) {
                    T source = parser.apply(tokens.get(0));
                    T target = parser.apply(tokens.get(1));
                    graph.addEdge(source, target);
                } else {
                    throw new IllegalArgumentException("Invalid file format in line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + filepath, e);
        }
    }
}
