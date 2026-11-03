package ru.nsu.oop.model;


/**
 * Перечисление возможных оценок и статусов зачетов.
 */
public enum Grade {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    UNSATISFACTORY(2),
    PASSED(0),
    FAILED(0);

    private final int numericValue;

    Grade(int numericValue) {
        this.numericValue = numericValue;
    }

    /**
     * Возвращает числовой эквивалент оценки для расчета среднего балла. Для зачетов (PASSED/FAILED)
     * возвращает 0.
     */
    public int getNumericValue() {
        return numericValue;
    }
}
