package ru.nsu.oop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.ControlType;
import ru.nsu.oop.model.CreditMark;
import ru.nsu.oop.model.CreditStudyRecord;
import ru.nsu.oop.model.Grade;
import ru.nsu.oop.model.GradedStudyRecord;
import ru.nsu.oop.model.StudyCreditBook;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyCreditBookAnalyzerTest {

    private StudyCreditBook book;

    @BeforeEach
    void setUp() {
        book = new StudyCreditBook(1, true);
    }

    // ===================================================================================
    // 1. ТЕСТЫ ДЛЯ calculateAverageGrade (Средний балл)
    // ===================================================================================

    @Test
    void calculateAverageGrade_emptyBook_returnsZero() {
        assertEquals(0.0, StudyCreditBookAnalyzer.calculateAverageGrade(book), 0.001);
    }

    @Test
    void calculateAverageGrade_onlyCredits_returnsZero() {
        book.addNewSemester(1, List.of(
            new CreditStudyRecord("Физра", CreditMark.PASSED)
        ));
        assertEquals(0.0, StudyCreditBookAnalyzer.calculateAverageGrade(book), 0.001);
    }

    @Test
    void calculateAverageGrade_mixedGrades_calculatesCorrectly() {
        book.addNewSemester(1, List.of(
            new GradedStudyRecord("Матан", ControlType.EXAM, Grade.GOOD), // 4
            new GradedStudyRecord("ОС", ControlType.EXAM, Grade.EXCELLENT), // 5
            new CreditStudyRecord("Физра", CreditMark.PASSED) // Игнорируется
        ));
        assertEquals(4.5, StudyCreditBookAnalyzer.calculateAverageGrade(book), 0.001);
    }

    @Test
    void calculateAverageGrade_multipleSemesters_calculatesAcrossAll() {
        book.addNewSemester(1, List.of(new GradedStudyRecord("А", ControlType.EXAM, Grade.GOOD))); // 4
        book.addNewSemester(2, List.of(new GradedStudyRecord("Б", ControlType.EXAM, Grade.SATISFACTORY))); // 3
        book.updateCurrentSemester(2);

        assertEquals(3.5, StudyCreditBookAnalyzer.calculateAverageGrade(book), 0.001);
    }

    // ===================================================================================
    // 2. ТЕСТЫ ДЛЯ canGetIncreasedStudentship (Повышенная стипендия)
    // ===================================================================================

    @Test
    void canGetIncreasedStudentship_firstSemester_returnsFalse() {
        assertFalse(StudyCreditBookAnalyzer.canGetIncreasedStudentship(book));
    }

    @Test
    void canGetIncreasedStudentship_allExcellentAndPassed_returnsTrue() {
        book.addNewSemester(1, List.of(
            new GradedStudyRecord("Матан", ControlType.EXAM, Grade.EXCELLENT),
            new CreditStudyRecord("Физра", CreditMark.PASSED)
        ));
        book.updateCurrentSemester(2);

        assertTrue(StudyCreditBookAnalyzer.canGetIncreasedStudentship(book));
    }

    @Test
    void canGetIncreasedStudentship_hasGoodGrade_returnsFalse() {
        book.addNewSemester(1, List.of(
            new GradedStudyRecord("Матан", ControlType.EXAM, Grade.GOOD)
        ));
        book.updateCurrentSemester(2);

        assertFalse(StudyCreditBookAnalyzer.canGetIncreasedStudentship(book));
    }

    @Test
    void canGetIncreasedStudentship_hasFailedCredit_returnsFalse() {
        book.addNewSemester(1, List.of(
            new GradedStudyRecord("Матан", ControlType.EXAM, Grade.EXCELLENT),
            new CreditStudyRecord("Физра", CreditMark.FAILED)
        ));
        book.updateCurrentSemester(2);

        assertFalse(StudyCreditBookAnalyzer.canGetIncreasedStudentship(book));
    }

    // ===================================================================================
    // 3. ТЕСТЫ ДЛЯ canTransferToBudget (Перевод на бюджет)
    // ===================================================================================

    @Test
    void canTransferToBudget_alreadyOnBudget_returnsFalse() {
        book.setPaidEducation(false);
        assertFalse(StudyCreditBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_firstSemester_returnsFalse() {
        assertFalse(StudyCreditBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_twoPerfectSessions_returnsTrue() {
        book.addNewSemester(1, List.of(new GradedStudyRecord("А", ControlType.EXAM, Grade.GOOD)));
        book.addNewSemester(2, List.of(new GradedStudyRecord("Б", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(2);

        assertTrue(StudyCreditBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_hasSatisfactoryInLastTwoSessions_returnsFalse() {
        book.addNewSemester(1, List.of(new GradedStudyRecord("А", ControlType.EXAM, Grade.SATISFACTORY)));
        book.addNewSemester(2, List.of(new GradedStudyRecord("Б", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(2);

        assertFalse(StudyCreditBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_hasFailedCredit_returnsFalse() {
        book.addNewSemester(1, List.of(new CreditStudyRecord("Физра", CreditMark.FAILED)));
        book.addNewSemester(2, List.of(new GradedStudyRecord("Б", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(2);

        assertFalse(StudyCreditBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_satisfactoryWasLongAgo_returnsTrue() {
        book.addNewSemester(1, List.of(new GradedStudyRecord("А", ControlType.EXAM, Grade.SATISFACTORY)));
        book.addNewSemester(2, List.of(new GradedStudyRecord("Б", ControlType.EXAM, Grade.EXCELLENT)));
        book.addNewSemester(3, List.of(new GradedStudyRecord("В", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(3);

        assertTrue(StudyCreditBookAnalyzer.canTransferToBudget(book));
    }

    // ===================================================================================
    // 4. ТЕСТЫ ДЛЯ canGetRedDiploma (Красный диплом)
    // ===================================================================================

    @Test
    void canGetRedDiploma_emptyBook_returnsFalse() {
        assertFalse(StudyCreditBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_hasSatisfactory_returnsFalse() {
        book.addNewSemester(1, List.of(
            new GradedStudyRecord("А", ControlType.EXAM, Grade.EXCELLENT),
            new GradedStudyRecord("Б", ControlType.EXAM, Grade.SATISFACTORY)
        ));
        assertFalse(StudyCreditBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_exactly75PercentExcellent_returnsTrue() {
        book.addNewSemester(1, List.of(
            new GradedStudyRecord("А", ControlType.EXAM, Grade.EXCELLENT),
            new GradedStudyRecord("Б", ControlType.EXAM, Grade.EXCELLENT),
            new GradedStudyRecord("В", ControlType.EXAM, Grade.EXCELLENT),
            new GradedStudyRecord("Г", ControlType.EXAM, Grade.GOOD)
        ));
        assertTrue(StudyCreditBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_lessThan75PercentExcellent_returnsFalse() {
        book.addNewSemester(1, List.of(
            new GradedStudyRecord("А", ControlType.EXAM, Grade.EXCELLENT),
            new GradedStudyRecord("Б", ControlType.EXAM, Grade.EXCELLENT),
            new GradedStudyRecord("В", ControlType.EXAM, Grade.GOOD),
            new GradedStudyRecord("Г", ControlType.EXAM, Grade.GOOD)
        ));
        assertFalse(StudyCreditBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_overwrittenGrade_returnsTrue() {
        book.addNewSemester(1, List.of(new GradedStudyRecord("Матан", ControlType.EXAM, Grade.SATISFACTORY)));
        book.addNewSemester(2, List.of(new GradedStudyRecord("Матан", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(2);

        assertTrue(StudyCreditBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_vkrDefenseNotExcellent_returnsFalse() {
        book.addNewSemester(1, List.of(new GradedStudyRecord("А", ControlType.EXAM, Grade.EXCELLENT)));
        book.addNewSemester(8, List.of(new GradedStudyRecord("Диплом", ControlType.VKR_DEFENSE, Grade.GOOD)));
        book.updateCurrentSemester(8);

        assertFalse(StudyCreditBookAnalyzer.canGetRedDiploma(book));
    }
}