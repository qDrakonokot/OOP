package ru.nsu.oop.substring;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
     * составляет O(M + K), где M — длина искомой подстроки, K - количество вхождений подстроки.
     *
     * @param reader поток символов для чтения (например, FileReader или StringReader)
     * @param target искомая подстрока
     * @return список индексов (начиная с 0), с которых начинается каждое вхождение подстроки
     * @throws IllegalArgumentException если искомая подстрока равна null или пуста
     * @throws IOException              если произошла ошибка при чтении из потока
     */
    public static List<Integer> find(Reader reader, String target)
            throws IOException {
        if (target == null) {
            throw new IllegalArgumentException("Передан null, вместо валидной строки");
        } else if (target.isEmpty()) {
            return Collections.emptyList();
        }

        StringBuilder window = createWindow(reader, target);
        if (window == null) {
            return Collections.emptyList();
        }

        return findSubStrings(reader, window, target);
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

        try (Reader reader = Files.newBufferedReader(
            Path.of(filename))) {
            return find(reader, target);
        }
    }

    private static StringBuilder createWindow(Reader reader, String target) throws IOException {
        StringBuilder window = new StringBuilder(target.length());
        for (int i = 0; i < target.length(); ++i) {
            int data = reader.read();
            if (data == -1) {
                return null;
            }
            window.append(Character.toString(data));
        }

        return window;
    }

    private static List<Integer> findSubStrings(Reader reader, StringBuilder window, String target)
            throws IOException {
        List<Integer> result = new ArrayList<>();
        int globalIndex = 0;
        if (window.toString().equals(target)) {
            result.add(globalIndex);
        }

        int data = reader.read();
        while (data != -1) {
            window.delete(0, 1);
            window.append(Character.toString(data).toLowerCase(Locale.ROOT));
            globalIndex++;

            if (window.toString().equals(target)) {
                result.add(globalIndex);
            }

            data = reader.read();
        }

        return result;
    }
}
