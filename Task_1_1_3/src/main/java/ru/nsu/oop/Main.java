package ru.nsu.oop;

import ru.nsu.oop.expression.bricks.Expression;
import ru.nsu.oop.expression.parsers.ExpressionParser;

/**
 * Мейн.
 */
public class Main {

    /**
     * Метод мейн.
     *
     * @param args Аргументы мейна (опять не используются).
     */
    public static void main(String[] args) {

        String input = "(3+(2*x))";
        Expression expr = ExpressionParser.parse(input);

        System.out.println("Parsed expression: ");
        expr.print();

        int result = expr.eval("x = 10; y = 13");
        System.out.println("Result with x=10: " + result);

        Expression derivative = expr.derivative("x");
        System.out.println("Derivative by x: ");
        derivative.print();
    }
}