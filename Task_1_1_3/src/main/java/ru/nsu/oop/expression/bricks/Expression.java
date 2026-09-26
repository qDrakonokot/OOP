package ru.nsu.oop.expression.bricks;

import java.util.Map;

import ru.nsu.oop.expression.parsers.VariableParser;
import ru.nsu.oop.output.ConsoleOutput;
import ru.nsu.oop.output.Output;

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
     * Преобразует выражение в строку.
     *
     * @return строковое представление выражения.
     */
    @Override
    public abstract String toString();

    /**
     * Архитектурно правильный print, не нарушающий Dip.
     *
     * @param output Реализация интерфейса вывода.
     */
    public final void print(Output output) {
        output.write(this.toString());
    }

    /**
     * Перегруженный print из условия задачи. По умолчанию выводит в консоль.
     */
    public final void print() {
        // По умолчанию выводим в консоль (требования задачи).
        print(new ConsoleOutput());
    }

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
