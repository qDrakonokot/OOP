package ru.nsu.oop;

import java.util.ConcurrentModificationException;
import ru.nsu.oop.hashmap.HashTable;

/**
 * Демонстрация работы хеш-таблицы.
 *
 * <p>Показывает основные операции: вставку, обновление, поиск, удаление,
 * итерацию, fail-fast защиту и логическое сравнение таблиц.</p>
 */
public class Main {

    /**
     * Точка входа.
     */
    public static void main() {
        System.out.println("=== ДЕМОНСТРАЦИЯ РАБОТЫ ХЕШ-ТАБЛИЦЫ ===");

        HashTable<String, Integer> scores = new HashTable<>();
        scores.put("Alice", 95);
        scores.put("Bob", 80);
        scores.put("Charlie", 90);

        System.out.println("\n1. Начальная таблица:");
        System.out.println(scores);

        scores.put("Bob", 85);
        System.out.println("\n2. После обновления баллов Bob (80 -> 85):");
        System.out.println(scores);

        System.out.println("\n3. Поиск элементов:");
        System.out.println("Баллы Alice: " + scores.get("Alice"));
        System.out.println("Есть ли Dave в таблице? -> " + scores.containsKey("Dave"));

        Integer removedScore = scores.remove("Charlie");
        System.out.println("\n4. После удаления Charlie (его баллы были: " + removedScore + "):");
        System.out.println(scores);

        System.out.println("\n5. Итерирование по таблице:");
        for (HashTable.Entry<String, Integer> entry : scores) {
            System.out.println("Студент: " + entry.getKey() + ", Баллы: " + entry.getValue());
        }

        System.out.println("\n6. Демонстрация защиты от конкурентных модификаций (Fail-Fast):");
        try {
            for (HashTable.Entry<String, Integer> entry : scores) {
                System.out.println("Читаем: " + entry.getKey());
                if (entry.getKey().equals("Alice")) {
                    System.out.println(">>> Пытаемся добавить Dave во время итерации...");
                    scores.put("Dave", 50);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ОШИБКА ПЕРЕХВАЧЕНА: " + e.getClass().getSimpleName());
            System.out.println("Итератор успешно заблокировал чтение измененной таблицы!");
        }

        System.out.println("\n7. Сравнение таблиц (equals):");
        HashTable<String, Integer> scoresCopy = new HashTable<>();
        scoresCopy.put("Bob", 85);
        scoresCopy.put("Alice", 95);
        scoresCopy.put("Dave", 50);

        System.out.println("Таблица 1: " + scores);
        System.out.println("Таблица 2: " + scoresCopy);
        System.out.println("Равны ли они логически? -> " + scores.equals(scoresCopy));
    }
}