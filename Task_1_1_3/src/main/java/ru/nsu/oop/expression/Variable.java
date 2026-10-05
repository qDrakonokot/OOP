package ru.nsu.oop.expression;

import java.util.Map;

/** Представляет переменную с заданным именем. */
public class Variable extends Expression {

    private final String name;

    /**
     * Создаёт переменную с заданным именем.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Expression derivative(String variableName) {
        if (this.name.equals(variableName)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return variables.get(this.name);
    }
}
