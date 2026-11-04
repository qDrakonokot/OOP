package ru.nsu.oop;

import ru.nsu.oop.model.ControlType;
import ru.nsu.oop.model.CreditMark;
import ru.nsu.oop.model.CreditStudyRecord;
import ru.nsu.oop.model.Grade;
import ru.nsu.oop.model.GradedStudyRecord;
import ru.nsu.oop.model.StudyCreditBook;
import ru.nsu.oop.service.StudyCreditBookAnalyzer;

import java.util.List;

/**
 * Мейн с демонстрацией.
 */
public class Main {

    /**
     * Метод мейна.
     *
     * @param args не используются.
     */
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ ЭЛЕКТРОННОЙ ЗАЧЕТНОЙ КНИЖКИ ===\n");

        StudyCreditBook book = new StudyCreditBook(1, true);
        System.out.println("Студент поступил на 1-й курс (Платное обучение).");

        book.addNewSemester(1, List.of(
            new GradedStudyRecord("Математический анализ", ControlType.EXAM, Grade.GOOD),
            new GradedStudyRecord("Программирование", ControlType.EXAM, Grade.EXCELLENT),
            new CreditStudyRecord("Физкультура", CreditMark.PASSED) // Обычный зачет
        ));
        System.out.println("\n--- Итоги 1-го семестра ---");
        printStats(book);

        book.updateCurrentSemester(2);
        book.addNewSemester(2, List.of(
            new GradedStudyRecord("Математический анализ", ControlType.EXAM, Grade.EXCELLENT), // Пересдал на 5!
            new GradedStudyRecord("Программирование", ControlType.EXAM, Grade.EXCELLENT),
            new GradedStudyRecord("Английский язык", ControlType.DIFF_CREDIT, Grade.EXCELLENT)
        ));
        System.out.println("\n--- Итоги 2-го семестра ---");
        printStats(book);

        if (StudyCreditBookAnalyzer.canTransferToBudget(book)) {
            System.out.println(">>> УРА! Студент переведен на БЮДЖЕТ!");
            book.setPaidEducation(false);
        }

        book.updateCurrentSemester(8);
        book.addNewSemester(8, List.of(
            new GradedStudyRecord("Защита диплома", ControlType.VKR_DEFENSE, Grade.EXCELLENT)
        ));
        System.out.println("\n--- Итоги 8-го семестра (Выпуск) ---");
        printStats(book);
    }

    private static void printStats(StudyCreditBook book) {
        System.out.printf("Средний балл за все время: %.2f%n", StudyCreditBookAnalyzer.calculateAverageGrade(book));
        System.out.println("Форма обучения: " + (book.isPaidEducation() ? "Бюджет" : "Платная"));
        System.out.println("Повышенная стипендия в этом семестре: " +
            (StudyCreditBookAnalyzer.canGetIncreasedStudentship(book) ? "ДА" : "НЕТ"));
        System.out.println("Идет на красный диплом: " +
            (StudyCreditBookAnalyzer.canGetRedDiploma(book) ? "ДА" : "НЕТ"));
    }
}