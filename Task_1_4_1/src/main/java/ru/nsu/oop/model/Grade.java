package ru.nsu.oop.model;

public enum Grade {
    EXCELLENT (5),
    GOOD (4),
    SATISFACTORY (3),
    UNSATISFACTORY (2),
    PASSED (0),
    FAILED (0);

    private final int numericValue;

    Grade(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }
}
