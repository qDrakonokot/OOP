package ru.nsu.oop.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.oop.model.ControlType;
import ru.nsu.oop.model.Grade;
import ru.nsu.oop.model.Record;
import ru.nsu.oop.model.RecordBook;

/**
 * Утилитный сервис для анализа успеваемости студента. Содержит бизнес-правила для расчета
 * стипендий, дипломов и переводов.
 */
public final class RecordBookAnalyzer {

    private RecordBookAnalyzer() {

    }

    /**
     * Вычисляет текущий средний балл за все время обучения. Учитываются только оценки, имеющие
     * числовой эквивалент (экзамены, диф. зачеты, ВКР). Обычные зачеты игнорируются.
     *
     * @param book зачетная книжка студента
     * @return средний балл, либо 0.0, если нет ни одной числовой оценки
     */
    public static double calculateAverageGrade(RecordBook book) {
        int totalGrade = 0;
        int gradesCnt = 0;
        for (int i = 1; i <= book.getCurrentSemester(); ++i) {
            List<Record> recordList = book.getRecordsForSemester(i);

            for (var record : recordList) {
                if (record.controlType() != ControlType.CREDIT) {
                    totalGrade += record.grade().getNumericValue();
                    gradesCnt++;
                }
            }
        }

        return gradesCnt == 0 ? 0.0 : (double) totalGrade / gradesCnt;
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре. Правило: все
     * экзамены и диф. зачеты за ПРЕДЫДУЩИЙ семестр должны быть сданы на "Отлично", а все обычные
     * зачеты должны быть сданы ("Зачет").
     *
     * @param book зачетная книжка студента
     * @return true, если студент имеет право на повышенную стипендию
     */
    public static boolean canGetIncreasedStudentship(RecordBook book) {
        int previousSemester = book.getCurrentSemester() - 1;
        if (previousSemester < 1) {
            return false;
        }

        for (var record : book.getRecordsForSemester(previousSemester)) {
            if (record.controlType() == ControlType.CREDIT && record.grade() != Grade.PASSED) {
                return false;
            } else if (record.controlType() != ControlType.CREDIT
                && record.grade() != Grade.EXCELLENT) {
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
    public static boolean canGetRedDiploma(RecordBook book) {
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

        if ((double) excellentGradesCnt / totalGradesCnt < 0.75) {
            return false;
        }

        for (int i = 1; i <= book.getCurrentSemester(); i++) {
            for (var record : book.getRecordsForSemester(i)) {
                if (record.controlType() == ControlType.VKR_DEFENSE) {
                    if (record.grade() != Grade.EXCELLENT) {
                        return false;
                    }
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
    public static boolean canTransferToBudget(RecordBook book) {
        if (!book.isPaidEducation()) {
            return false;
        }
        int currentSemester = book.getCurrentSemester();
        if (currentSemester < 2) {
            return false;
        }

        for (int i = currentSemester - 1; i <= currentSemester; i++) {
            for (var record : book.getRecordsForSemester(i)) {
                if (record.controlType() == ControlType.CREDIT && record.grade() == Grade.FAILED) {
                    return false;
                }
                if (record.controlType() != ControlType.CREDIT
                    && record.grade().getNumericValue() <= Grade.SATISFACTORY.getNumericValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    private static Collection<Grade> getDiplomaGrades(RecordBook book) {
        Map<String, Grade> diplomaGrades = new HashMap<>();

        for (int i = 1; i <= book.getCurrentSemester(); ++i) {
            for (var record : book.getRecordsForSemester(i)) {
                if (record.controlType() == ControlType.CREDIT) {
                    continue;
                }
                diplomaGrades.put(record.subjectName(), record.grade());
            }
        }

        return diplomaGrades.values();
    }

}
