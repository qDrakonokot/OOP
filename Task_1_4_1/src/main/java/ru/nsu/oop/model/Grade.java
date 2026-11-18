package ru.nsu.oop.model;


/**
 * Перечисление возможных дифференцированных (числовых) оценок.
 */
public enum Grade {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    UNSATISFACTORY(2);

    private final int numericValue;

    Grade(int numericValue) {
        this.numericValue = numericValue;
    }

    /**
     * Возвращает числовой эквивалент оценки для расчета среднего балла.
     */
    public int getNumericValue() {
        return numericValue;
    }
}
