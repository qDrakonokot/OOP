package ru.nsu.oop.expression;

import java.util.HashMap;
import java.util.Map;

/** Представляет выражение, которое можно вычислять и дифференцировать. */
public abstract class Expression {
    public abstract String toString();

    /** Выводит выражение в стандартный поток вывода. */
    public void print() {
        System.out.println(toString());
    }

    /**
     * Вычисляет символьную производную, сохраняя исходное выражение.
     *
     * @param variableName имя переменной, по которой вычисляется производная
     * @return производная в виде нового выражения
     */
    public abstract Expression derivative(String variableName);

    /**
     * Вычисляет значение по присваиваниям вида {@code x = 10; y = 13}.
     * Пустая строка задаёт пустой набор значений переменных.
     *
     * @param assignments присваивания переменным, разделённые точкой с запятой
     * @return целочисленное значение выражения
     */
    public int eval(String assignments) {
        Map<String, Integer> variables = new HashMap<>();
        if (assignments.isBlank()) {
            return this.eval(variables);
        }
        for (String assignment : assignments.split(";")) {
            String name = assignment.split("=")[0].trim();
            int value = Integer.parseInt(assignment.split("=")[1].trim());
            variables.put(name, value);
        }
        return this.eval(variables);
    }

    /**
     * Вычисляет выражение с заданными значениями переменных.
     *
     * @param variables соответствие имён переменных их целочисленным значениям
     * @return целочисленное значение выражения
     */
    public abstract int eval(Map<String, Integer> variables);
}
