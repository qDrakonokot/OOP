package ru.nsu.oop.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import ru.nsu.oop.model.ControlType;
import ru.nsu.oop.model.CreditMark;
import ru.nsu.oop.model.CreditStudyRecord;
import ru.nsu.oop.model.Grade;
import ru.nsu.oop.model.GradedStudyRecord;
import ru.nsu.oop.model.StudyCreditBook;

/**
 * Утилитный сервис для анализа успеваемости студента. Содержит бизнес-правила для расчета
 * стипендий, дипломов и переводов.
 */
public final class StudyCreditBookAnalyzer {

    private static final double PERCENTAGE_OF_EXCELLENT = 0.75;
    private static final double NO_GRADES = 0.0;
    private static final int SECOND_SEMESTER = 2;
    private static final int FIRST_SEMESTER = 1;

    private StudyCreditBookAnalyzer() {

    }

    /**
     * Вычисляет текущий средний балл за все время обучения. Учитываются только записи типа
     * {@link GradedStudyRecord} (экзамены, диф. зачеты, ВКР). Обычные зачеты
     * ({@link CreditStudyRecord}) автоматически игнорируются.
     *
     * @param book зачетная книжка студента
     * @return средний балл, либо 0.0, если нет ни одной числовой оценки
     */
    public static double calculateAverageGrade(StudyCreditBook book) {
        int totalGrade = 0;
        int gradesCnt = 0;
        for (int i = 1; i <= book.getCurrentSemester(); ++i) {
            for (var studyRecord : book.getRecordsForSemester(i)) {
                if (studyRecord instanceof GradedStudyRecord graded) {
                    totalGrade += graded.grade().getNumericValue();
                    gradesCnt++;
                }
            }
        }

        // Возвращаем ноль, если нет оценок.
        return gradesCnt == 0 ? NO_GRADES : (double) totalGrade / gradesCnt;
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре. Правило: все
     * экзамены и диф. зачеты за ПРЕДЫДУЩИЙ семестр должны быть сданы на "Отлично", а все обычные
     * зачеты должны быть сданы (PASSED).
     *
     * @param book зачетная книжка студента
     * @return true, если студент имеет право на повышенную стипендию
     */
    public static boolean canGetIncreasedStudentship(StudyCreditBook book) {
        int previousSemester = book.getCurrentSemester() - 1;
        if (previousSemester < FIRST_SEMESTER) {
            return false;
        }

        for (var studyRecord : book.getRecordsForSemester(previousSemester)) {
            if (studyRecord instanceof CreditStudyRecord credit
                && credit.mark() != CreditMark.PASSED) {
                return false;
            }
            if (studyRecord instanceof GradedStudyRecord graded
                && graded.grade() != Grade.EXCELLENT) {
                return false;
            }
        }

        return true;
    }

    /**
     * Прогнозирует возможность получения диплома с отличием ("красного" диплома). Правила: 1.
     * Отсутствие итоговых оценок "Удовлетворительно" и ниже. 2. Не менее 75% итоговых оценок
     * (последняя оценка по каждому предмету) — "Отлично". 3. Квалификационная работа (ВКР) защищена
     * на "Отлично" (если защита уже состоялась).
     *
     * @param book зачетная книжка студента
     * @return true, если студент сохраняет шансы на красный диплом
     */
    public static boolean canGetRedDiploma(StudyCreditBook book) {
        Collection<Grade> diplomaGrades = getDiplomaGrades(book);
        if (diplomaGrades.isEmpty()) {
            return false;
        }
        int excellentGradesCnt = 0;
        int totalGradesCnt = diplomaGrades.size();

        for (var grade : diplomaGrades) {
            if (grade == Grade.EXCELLENT) {
                excellentGradesCnt++;
            }
            if (grade.getNumericValue() <= Grade.SATISFACTORY.getNumericValue()) {
                return false;
            }
        }

        if ((double) excellentGradesCnt / totalGradesCnt < PERCENTAGE_OF_EXCELLENT) {
            return false;
        }

        for (int i = 1; i <= book.getCurrentSemester(); i++) {
            for (var studyRecord : book.getRecordsForSemester(i)) {
                if (studyRecord instanceof GradedStudyRecord graded
                    && graded.controlType() == ControlType.VKR_DEFENSE
                    && graded.grade() != Grade.EXCELLENT) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Проверяет возможность перевода студента с платного обучения на бюджетное. Правило: отсутствие
     * оценок "Удовлетворительно" и ниже (включая незачеты) за последние ДВЕ экзаменационные
     * сессии.
     *
     * @param book зачетная книжка студента
     * @return true, если студент имеет право подать заявление на перевод
     */
    public static boolean canTransferToBudget(StudyCreditBook book) {
        if (!book.isPaidEducation()) {
            return false;
        }
        int currentSemester = book.getCurrentSemester();
        if (currentSemester < SECOND_SEMESTER) {
            return false;
        }

        for (int i = currentSemester - 1; i <= currentSemester; i++) {
            for (var studyRecord : book.getRecordsForSemester(i)) {
                if (studyRecord instanceof CreditStudyRecord credit
                    && credit.mark() == CreditMark.FAILED) {
                    return false;
                }
                if (studyRecord instanceof GradedStudyRecord graded
                    && graded.grade().getNumericValue() <= Grade.SATISFACTORY.getNumericValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    private static Collection<Grade> getDiplomaGrades(StudyCreditBook book) {
        Map<String, Grade> diplomaGrades = new HashMap<>();

        for (int i = 1; i <= book.getCurrentSemester(); ++i) {
            for (var studyRecord : book.getRecordsForSemester(i)) {
                if (studyRecord instanceof GradedStudyRecord graded) {
                    diplomaGrades.put(graded.subjectName(), graded.grade());
                }
            }
        }

        return diplomaGrades.values();
    }

}
