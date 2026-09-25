package ru.nsu.oop.expression.bricks;

import java.util.Map;

public final class Number extends Expression {

    private final int value;

    public Number(int value) {
        this.value = value;
    }


    @Override
    protected int calculate(Map<String, Integer> variablesValues) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Expression derivative(String variableName) {
        return new Number(0);
    }
}
