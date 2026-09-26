package ru.nsu.oop;

import ru.nsu.oop.expression.bricks.Expression;
import ru.nsu.oop.expression.operations.ExpressionParser;

public class Main {

    static void main(String[] args) {

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