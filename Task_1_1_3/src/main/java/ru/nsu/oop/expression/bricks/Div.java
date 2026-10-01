package ru.nsu.oop.expression.bricks;

import java.util.Map;

/**
 * Класс реализующий узел деления в AST.
 */
public final class Div extends Expression {

    private final Expression left;
    private final Expression right;

    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    protected int calculate(Map<String, Integer> variablesValues) {
        return left.calculate(variablesValues) / right.calculate(variablesValues);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    @Override
    public Expression derivative(String variableName) {
        return new Div(
            new Sub(
                (
                    new Mul(left.derivative(variableName), right)
                ),
                (
                    new Mul(left, right.derivative(variableName))
                )
            ),
            new Mul(right, right)
        );


    }
}
