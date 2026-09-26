package ru.nsu.oop.expression.bricks;

import java.util.Map;
import java.util.Objects;

/**
 * Класс реализующий лист, представляющий переменную, в AST.
 */
public final class Variable extends Expression {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    protected int calculate(Map<String, Integer> variablesValues) {
        Integer value = variablesValues.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Variable '"
                + name + "' is not defined in the context.");
        }
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Expression derivative(String variableName) {
        if (Objects.equals(variableName, name)) {
            return new Number(1);
        } else {
            return new Number(0);
        }

    }
}
