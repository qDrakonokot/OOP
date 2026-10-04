package ru.nsu.oop;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression {
    public abstract String toString();

    public void print() {
        System.out.println(toString());
    }

    public abstract Expression derivative(String variableName);

    public int eval(String assignments) {
        Map<String, Integer> variables = new HashMap<>();
        for (String assignment : assignments.split(";")) {
            String name = assignment.split("=")[0].trim();
            int value = Integer.parseInt(assignment.split("=")[1].trim());
            variables.put(name, value);
        }
        return this.eval(variables);
    }

    public abstract int eval(Map<String, Integer> variables);
}
