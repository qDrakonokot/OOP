package ru.nsu.oop.model;

/**
 * Перечисление форм контроля знаний, за которые выставляется дифференцированная оценка.
 * Обычные зачеты здесь отсутствуют, так как для них используется отдельная сущность
 * {@link CreditStudyRecord}.
 */
public enum ControlType {
    EXAM,
    DIFF_CREDIT,
    VKR_DEFENSE
}
