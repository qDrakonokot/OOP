package ru.nsu.oop;

import ru.nsu.oop.model.ControlType;
import ru.nsu.oop.model.Grade;
import ru.nsu.oop.model.Record;
import ru.nsu.oop.model.RecordBook;
import ru.nsu.oop.service.RecordBookAnalyzer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ ЭЛЕКТРОННОЙ ЗАЧЕТНОЙ КНИЖКИ ===\n");

        // Создаем зачетку: студент на 1-м семестре, учится платно
        RecordBook book = new RecordBook(1, true);
        System.out.println("Студент поступил на 1-й курс (Платное обучение).");

        // ================= СЕМЕСТР 1 =================
        book.addNewSemester(1, List.of(
            new Record("Математический анализ", ControlType.EXAM, Grade.GOOD),
            new Record("Программирование", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Физкультура", ControlType.CREDIT, Grade.PASSED)
        ));
        System.out.println("\n--- Итоги 1-го семестра ---");
        printStats(book);

        // ================= СЕМЕСТР 2 =================
        book.updateCurrentSemester(2);
        book.addNewSemester(2, List.of(
            new Record("Математический анализ", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Программирование", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Английский язык", ControlType.DIFF_CREDIT, Grade.EXCELLENT)
        ));
        System.out.println("\n--- Итоги 2-го семестра ---");
        printStats(book);

        // Пытаемся перевестись на бюджет (2 сессии без троек)
        if (RecordBookAnalyzer.canTransferToBudget(book)) {
            System.out.println(">>> УРА! Студент переведен на БЮДЖЕТ!");
            book.setPaidEducation(false);
        }

        // ================= СЕМЕСТР 8 (Выпуск) =================
        book.updateCurrentSemester(8);
        book.addNewSemester(8, List.of(
            new Record("Защита диплома", ControlType.VKR_DEFENSE, Grade.EXCELLENT)
        ));
        System.out.println("\n--- Итоги 8-го семестра (Выпуск) ---");
        printStats(book);
    }
    
    private static void printStats(RecordBook book) {
        System.out.printf("Средний балл за все время: %.2f%n", RecordBookAnalyzer.calculateAverageGrade(book));
        System.out.println("Форма обучения: " + (book.isPaidEducation() ? "Платная" : "Бюджет"));
        System.out.println("Повышенная стипендия в этом семестре: " +
            (RecordBookAnalyzer.canGetIncreasedStudentship(book) ? "ДА" : "НЕТ"));
        System.out.println("Идет на красный диплом: " +
            (RecordBookAnalyzer.canGetRedDiploma(book) ? "ДА" : "НЕТ"));
    }
}