package ru.nsu.oop;

import java.util.List;
import ru.nsu.oop.graph.model.AdjacencyList;
import ru.nsu.oop.graph.model.AdjacencyMatrix;
import ru.nsu.oop.graph.model.Graph;
import ru.nsu.oop.graph.model.IncidenceMatrix;
import ru.nsu.oop.graph.sort.DfsTopoSort;
import ru.nsu.oop.graph.utils.GraphReader;

/**
 * Мэйн
 */
public class Main {

    /**
     * Примеры работы графов в мэйне.
     */
    public static void main() {
        System.out.println("=== ДЕМОНСТРАЦИЯ РАБОТЫ ГРАФОВ ===");

        Graph<String> projectGraph = new AdjacencyList<>();

        projectGraph.addEdge("Написать код", "Скомпилировать");
        projectGraph.addEdge("Написать тесты", "Скомпилировать");
        projectGraph.addEdge("Скомпилировать", "Запустить тесты");
        projectGraph.addEdge("Запустить тесты", "Сделать релиз");
        projectGraph.addEdge("Написать документацию", "Сделать релиз");

        System.out.println("\n1. Структура графа (Список смежности):");
        System.out.println(projectGraph);

        System.out.println("\n2. Топологическая сортировка (Порядок выполнения задач):");
        List<String> sortedTasks = DfsTopoSort.TopoSort(projectGraph);
        for (int i = 0; i < sortedTasks.size(); i++) {
            System.out.println((i + 1) + ". " + sortedTasks.get(i));
        }

        System.out.println("\n3. Демонстрация полиморфизма (equals):");

        Graph<String> matrixGraph = new IncidenceMatrix<>();
        matrixGraph.addEdge("Написать код", "Скомпилировать");
        matrixGraph.addEdge("Написать тесты", "Скомпилировать");
        matrixGraph.addEdge("Скомпилировать", "Запустить тесты");
        matrixGraph.addEdge("Запустить тесты", "Сделать релиз");
        matrixGraph.addEdge("Написать документацию", "Сделать релиз");

        System.out.println("Граф 1 тип: " + projectGraph.getClass().getSimpleName());
        System.out.println("Граф 2 тип: " + matrixGraph.getClass().getSimpleName());

        boolean areEqual = projectGraph.equals(matrixGraph);
        System.out.println("Равны ли они логически? -> " + areEqual);

        System.out.println("\n4. Чтение из файла:");
        Graph<Integer> fileGraph = new AdjacencyMatrix<>();
        GraphReader.populateGraph(
            "/home/drakonokot/Coding/java/OOP/Task_1_2_1/src/main/java/ru/nsu/oop/graph.txt",
            fileGraph, Integer::parseInt);
        System.out.println(fileGraph);
    }
}