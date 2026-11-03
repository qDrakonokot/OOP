package ru.nsu.oop.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.oop.model.ControlType;
import ru.nsu.oop.model.Grade;
import ru.nsu.oop.model.Record;
import ru.nsu.oop.model.RecordBook;

public final class RecordBookAnalyzer {

    private RecordBookAnalyzer() {

    }

    public static double middleGrade(RecordBook book) {
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

        return (double) totalGrade / gradesCnt;
    }

    public static boolean satisfiesIncreasedStudentship(RecordBook book) {
        int previousSemester = book.getCurrentSemester() - 1;

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

    public static boolean satisfiesRedDiploma(RecordBook book) {
        Collection<Grade> diplomaGrades = getDiplomaGrades(book);
        int excellentGradesCnt = 0;
        int totalGradesCnt = diplomaGrades.size();

        for (var grade : diplomaGrades) {
            totalGradesCnt += grade == Grade.EXCELLENT ? 1 : 0;
            if (grade.getNumericValue() <= Grade.SATISFACTORY.getNumericValue()) {
                return false;
            }
        }

        if ((double) excellentGradesCnt / totalGradesCnt < 0.75) {
            return false;
        }

        for (var record : book.getRecordsForSemester(book.getCurrentSemester())) {
            if (record.controlType() == ControlType.VKR_DEFENSE
                && record.grade() != Grade.EXCELLENT) {
                    return false;
            }
        }

        return true;
    }

    public static boolean satisfiesTransferRules(RecordBook book) {
        if (!book.isPaidEducation()) {
            return true;
        }
        int currentSemester = book.getCurrentSemester();
        if (currentSemester <= 2) {
            return false;
        }

        List<Record> currentSemesterRecords = book.getRecordsForSemester(currentSemester);
        List<Record> previousSemesterRecords = book.getRecordsForSemester(currentSemester - 1);

        for (var record : currentSemesterRecords) {
            if (record.grade().getNumericValue() <= Grade.SATISFACTORY.getNumericValue()) {
                return false;
            }
        }

        for (var record : previousSemesterRecords) {
            if (record.grade().getNumericValue() <= Grade.SATISFACTORY.getNumericValue()) {
                return false;
            }
        }

        return true;
    }

    private static Collection<Grade> getDiplomaGrades(RecordBook book) {
        Map<String, Grade> diplomaGrades = new HashMap<>();

        for (int i = 1; i <= book.getCurrentSemester(); ++i) {
            List<Record> recordList = book.getRecordsForSemester(i);

            for (var record : recordList) {
                if (record.controlType() == ControlType.CREDIT) {
                    continue;
                }

                diplomaGrades.put(record.subjectName(), record.grade());
            }
        }

        return diplomaGrades.values();
    }

}
