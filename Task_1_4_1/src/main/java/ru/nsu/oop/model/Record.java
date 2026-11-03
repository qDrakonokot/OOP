package ru.nsu.oop.model;

/**
 * Неизменяемая запись об одной сдаче предмета в зачетной книжке.
 *
 * @param subjectName название дисциплины
 * @param controlType форма контроля (экзамен, зачет и т.д.)
 * @param grade       полученная оценка или статус зачета
 */
public record Record(
    String subjectName,
    ControlType controlType,
    Grade grade
) {

}
