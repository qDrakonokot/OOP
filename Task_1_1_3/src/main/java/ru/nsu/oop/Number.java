package ru.nsu.oop;

import java.util.Map;

public class Number extends Expression {
    private final int value;

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
