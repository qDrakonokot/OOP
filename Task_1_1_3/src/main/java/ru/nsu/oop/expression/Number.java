package ru.nsu.oop.expression;

import java.util.Map;

/** Представляет целочисленную константу в выражении. */
public class Number extends Expression {
    private final int value;

    /**
     * Создаёт целочисленную константу.
     *
     * @param value значение константы
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public Expression derivative(String variableName) {
        return new Number(0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }
}
