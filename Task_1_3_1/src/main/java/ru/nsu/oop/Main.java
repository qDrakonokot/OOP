package ru.nsu.oop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ru.nsu.oop.substring.Finder;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ ПОИСКА ПОДСТРОКИ ===\n");

        String filename = "demo_input.txt";
        String text = "абракадабра! Магия вне Хогвартса.";
        String target = "бра";

        try {
            System.out.println("1. Создаем файл '" + filename + "' с текстом:");
            System.out.println("   \"" + text + "\"");
            Files.writeString(Path.of(filename), text);

            System.out.println("\n2. Ищем подстроку: \"" + target + "\"");

            List<Integer> result = Finder.find(filename, target);

            System.out.println("   Найдено вхождений: " + result.size());
            System.out.println("   Индексы вхождений: " + result);

            String overlapTarget = "абра";
            System.out.println("\n3. Ищем подстроку: \"" + overlapTarget + "\"");
            List<Integer> overlapResult = Finder.find(filename, overlapTarget);
            System.out.println("   Индексы вхождений: " + overlapResult);

            System.out.println("\n4. Демонстрация защиты от ошибок:");
            try {
                Finder.find("ghost_file.txt", "тест");
            } catch (IOException e) {
                System.out.println("   Успешно перехвачена ошибка: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println(
                "Произошла критическая ошибка при работе с файлом: " + e.getMessage());
        } finally {
            try {
                Files.deleteIfExists(Path.of(filename));
                System.out.println("\n5. Временный файл '" + filename + "' успешно удален.");
            } catch (IOException e) {
                System.err.println("Не удалось удалить временный файл.");
            }
        }
    }
}