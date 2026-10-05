package ru.nsu.oop.console;

import ru.nsu.oop.expression.Expression;

/**
 * Выводит выражения и результаты вычислений.
 */
public class ConsoleView {
    /**
     * Выводит разобранное выражение.
     *
     * @param expression выражение для вывода
     */
    public void printExpression(Expression expression) {
        expression.print();
    }

    /**
     * Выводит результат вычисления.
     *
     * @param result значение выражения
     */
    public void printResult(int result) {
        System.out.println(result);
    }
}
