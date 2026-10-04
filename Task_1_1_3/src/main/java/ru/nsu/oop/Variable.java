package ru.nsu.oop;

import java.util.HashMap;
import java.util.Map;

public class Variable extends Expression {

    private final String name;

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
