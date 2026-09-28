package ru.nsu.oop.expression.bricks;

import java.util.Map;

import ru.nsu.oop.expression.parsers.VariableParser;

/**
 * Базовый класс для всех узлов AST арифметического выражения.
 */
public abstract class Expression {

    /**
     * Внутренний метод для вычисления выражений.
     *
     * @param variablesValues Мапа содержащая означенные переменные типа (x = 10).
     * @return результат вычисления выражения.
     */
    protected abstract int calculate(Map<String, Integer> variablesValues);

    /**
     * Метод численного дифференцирования выражения.
     *
     * @param variableName Имя переменной, по которой дифференцировать.
     * @return Новое выражение производной.
     */
    public abstract Expression derivative(String variableName);

    /**
     * Внешний API для вычисления значения выражения.
     *
     * @param variablesValues Строка вида (x = 10; y = 69), представляющая означивание переменных.
     * @return Результат вычисления выражения.
     */
    public final int eval(String variablesValues) {
        Map<String, Integer> variablesValuesMap = VariableParser.parse(variablesValues);
        return calculate(variablesValuesMap);
    }

}
