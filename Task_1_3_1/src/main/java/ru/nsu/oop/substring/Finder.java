package ru.nsu.oop.substring;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для нахождения подстроки в строке.
 * Строка может быть в любом Reader, также есть перегруженный метод для
 * поиска подстроки в текстовом файле.
 */
public class Finder {

    private Finder() {
    }

    /**
     * Ищет все вхождения заданной подстроки в потоке символов.
     * Алгоритм использует принцип "скользящего окна", что позволяет обрабатывать потоки данных
     * любого размера (включая файлы, превышающие объем оперативной памяти). Потребление памяти
     * составляет O(M), где M — длина искомой подстроки.
     *
     * @param reader поток символов для чтения (например, FileReader или StringReader)
     * @param target искомая подстрока
     * @return список индексов (начиная с 0), с которых начинается каждое вхождение подстроки
     * @throws IllegalArgumentException если искомая подстрока равна null или пуста
     * @throws IOException              если произошла ошибка при чтении из потока
     */
    public static List<Integer> find(Reader reader, String target)
        throws IOException {
        if (target == null || target.isEmpty()) {
            throw new IllegalArgumentException("Передана пустая подстрока для поиска");
        }

        List<Integer> result = new ArrayList<>();
        StringBuilder window = new StringBuilder();
        for (int i = 0; i < target.length(); ++i) {
            int data = reader.read();
            if (data == -1) {
                return result;
            }
            window.append(Character.toString(data));
        }

        int globalIndex = 0;
        if (window.toString().equals(target)) {
            result.add(globalIndex);
        }

        int data = reader.read();
        while (data != -1) {
            window.delete(0, 1);
            window.append(Character.toString(data));
            globalIndex++;

            if (window.toString().equals(target)) {
                result.add(globalIndex);
            }

            data = reader.read();
        }

        return result;
    }

    /**
     * Перегруженный метод для удобного поиска подстроки в файле по его имени. Открывает файл в
     * кодировке UTF-8 и делегирует работу основному алгоритму.
     *
     * @param filename имя файла (путь)
     * @param target   искомая подстрока
     * @return список индексов вхождений
     * @throws IOException если файл не найден или произошла ошибка чтения
     */
    public static List<Integer> find(String filename, String target) throws IOException {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым");
        }

        try (Reader reader = java.nio.file.Files.newBufferedReader(
            java.nio.file.Path.of(filename))) {
            return find(reader, target);
        }
    }
}
