package ru.nsu.oop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.ControlType;
import ru.nsu.oop.model.Grade;
import ru.nsu.oop.model.Record;
import ru.nsu.oop.model.RecordBook;

class RecordBookAnalyzerTest {

    private RecordBook book;

    @BeforeEach
    void setUp() {
        book = new RecordBook(1, true);
    }

    @Test
    void calculateAverageGrade_emptyBook_returnsZero() {
        assertEquals(0.0, RecordBookAnalyzer.calculateAverageGrade(book), 0.001);
    }

    @Test
    void calculateAverageGrade_onlyCredits_returnsZero() {
        book.addNewSemester(1, List.of(
            new Record("Физра", ControlType.CREDIT, Grade.PASSED)
        ));
        assertEquals(0.0, RecordBookAnalyzer.calculateAverageGrade(book), 0.001);
    }

    @Test
    void calculateAverageGrade_mixedGrades_calculatesCorrectly() {
        book.addNewSemester(1, List.of(
            new Record("Матан", ControlType.EXAM, Grade.GOOD),
            new Record("ОС", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Физра", ControlType.CREDIT, Grade.PASSED)
        ));
        assertEquals(4.5, RecordBookAnalyzer.calculateAverageGrade(book), 0.001);
    }

    @Test
    void calculateAverageGrade_multipleSemesters_calculatesAcrossAll() {
        book.addNewSemester(1,
            List.of(new Record("А", ControlType.EXAM, Grade.GOOD)));
        book.addNewSemester(2,
            List.of(new Record("Б", ControlType.EXAM, Grade.SATISFACTORY)));
        book.updateCurrentSemester(2);

        assertEquals(3.5, RecordBookAnalyzer.calculateAverageGrade(book), 0.001);
    }

    @Test
    void canGetIncreasedStudentship_firstSemester_returnsFalse() {
        assertFalse(RecordBookAnalyzer.canGetIncreasedStudentship(book));
    }

    @Test
    void canGetIncreasedStudentship_allExcellentAndPassed_returnsTrue() {
        book.addNewSemester(1, List.of(
            new Record("Матан", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Физра", ControlType.CREDIT, Grade.PASSED)
        ));
        book.updateCurrentSemester(2);

        assertTrue(RecordBookAnalyzer.canGetIncreasedStudentship(book));
    }

    @Test
    void canGetIncreasedStudentship_hasGoodGrade_returnsFalse() {
        book.addNewSemester(1, List.of(
            new Record("Матан", ControlType.EXAM, Grade.GOOD)
        ));
        book.updateCurrentSemester(2);

        assertFalse(RecordBookAnalyzer.canGetIncreasedStudentship(book));
    }

    @Test
    void canGetIncreasedStudentship_hasFailedCredit_returnsFalse() {
        book.addNewSemester(1, List.of(
            new Record("Матан", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Физра", ControlType.CREDIT, Grade.FAILED)
        ));
        book.updateCurrentSemester(2);

        assertFalse(RecordBookAnalyzer.canGetIncreasedStudentship(book));
    }

    @Test
    void canTransferToBudget_alreadyOnBudget_returnsFalse() {
        book.setPaidEducation(false);
        assertFalse(RecordBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_firstSemester_returnsFalse() {
        // Нужно минимум 2 закрытые сессии
        assertFalse(RecordBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_twoPerfectSessions_returnsTrue() {
        book.addNewSemester(1,
            List.of(new Record("А", ControlType.EXAM, Grade.GOOD)));
        book.addNewSemester(2,
            List.of(new Record("Б", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(2);

        assertTrue(RecordBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_hasSatisfactoryInLastTwoSessions_returnsFalse() {
        book.addNewSemester(1,
            List.of(new Record("А", ControlType.EXAM, Grade.SATISFACTORY)));
        book.addNewSemester(2,
            List.of(new Record("Б", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(2);

        assertFalse(RecordBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_hasFailedCredit_returnsFalse() {
        book.addNewSemester(1,
            List.of(new Record("Физра", ControlType.CREDIT, Grade.FAILED)));
        book.addNewSemester(2,
            List.of(new Record("Б", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(2);

        assertFalse(RecordBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canTransferToBudget_satisfactoryWasLongAgo_returnsTrue() {
        book.addNewSemester(1,
            List.of(new Record("А", ControlType.EXAM, Grade.SATISFACTORY)));
        book.addNewSemester(2,
            List.of(new Record("Б", ControlType.EXAM, Grade.EXCELLENT)));
        book.addNewSemester(3,
            List.of(new Record("В", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(3);

        assertTrue(RecordBookAnalyzer.canTransferToBudget(book));
    }

    @Test
    void canGetRedDiploma_emptyBook_returnsFalse() {
        assertFalse(RecordBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_hasSatisfactory_returnsFalse() {
        book.addNewSemester(1, List.of(
            new Record("А", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Б", ControlType.EXAM, Grade.SATISFACTORY)
        ));
        assertFalse(RecordBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_exactly75PercentExcellent_returnsTrue() {
        book.addNewSemester(1, List.of(
            new Record("А", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Б", ControlType.EXAM, Grade.EXCELLENT),
            new Record("В", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Г", ControlType.EXAM, Grade.GOOD)
        ));
        assertTrue(RecordBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_lessThan75PercentExcellent_returnsFalse() {
        book.addNewSemester(1, List.of(
            new Record("А", ControlType.EXAM, Grade.EXCELLENT),
            new Record("Б", ControlType.EXAM, Grade.EXCELLENT),
            new Record("В", ControlType.EXAM, Grade.GOOD),
            new Record("Г", ControlType.EXAM, Grade.GOOD)
        ));
        assertFalse(RecordBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_overwrittenGrade_returnsTrue() {
        book.addNewSemester(1,
            List.of(new Record("Матан", ControlType.EXAM, Grade.SATISFACTORY)));

        book.addNewSemester(2,
            List.of(new Record("Матан", ControlType.EXAM, Grade.EXCELLENT)));
        book.updateCurrentSemester(2);

        assertTrue(RecordBookAnalyzer.canGetRedDiploma(book));
    }

    @Test
    void canGetRedDiploma_vkrDefenseNotExcellent_returnsFalse() {
        book.addNewSemester(1,
            List.of(new Record("А", ControlType.EXAM, Grade.EXCELLENT)));
        book.addNewSemester(8,
            List.of(new Record("Диплом", ControlType.VKR_DEFENSE, Grade.GOOD)));
        book.updateCurrentSemester(8);

        assertFalse(RecordBookAnalyzer.canGetRedDiploma(book));
    }
}