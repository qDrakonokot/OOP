package ru.nsu.oop.model;

/**
 * Неизменяемая учебная запись об обычном зачете.
 * Не имеет формы контроля, так как по умолчанию является зачетом, и не имеет числовой оценки.
 *
 * @param subjectName название дисциплины
 * @param mark        статус сдачи зачета (сдан / не сдан)
 */
public record CreditStudyRecord(
    String subjectName,
    CreditMark mark
) implements StudyRecord {

}