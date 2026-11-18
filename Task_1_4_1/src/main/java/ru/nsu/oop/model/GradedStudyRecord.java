package ru.nsu.oop.model;

/**
 * Неизменяемая учебная запись, подразумевающая получение дифференцированной (числовой) оценки.
 * Используется для экзаменов, дифференцированных зачетов и защиты ВКР.
 *
 * @param subjectName название дисциплины
 * @param controlType форма контроля (экзамен, диф. зачет, ВКР)
 * @param grade       полученная оценка (от 2 до 5)
 */
public record GradedStudyRecord(
    String subjectName,
    ControlType controlType,
    Grade grade
) implements StudyRecord {

}
