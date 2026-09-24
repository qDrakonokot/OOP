package ru.nsu.oop.expression.bricks;

import java.util.Map;

public final class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    protected int calculate(Map<String, Integer> variablesValues) {
        return variablesValues.get(name);
    }

    @Override
    protected String asString() {
        return name;
    }

    @Override
    public Expression derivate(String variableName) {
        return null;
    }
}
