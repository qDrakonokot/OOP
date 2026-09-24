package ru.nsu.oop.expression.bricks;

import java.util.Map;

public final class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    protected int calculate(Map<String, Integer> variablesValues) {
        return left.calculate(variablesValues) * right.calculate(variablesValues);
    }

    @Override
    protected String asString() {
        return "(" + left.asString() + "*" + right.asString() + ")";
    }

    @Override
    public Expression derivative(String variableName) {
        return new Add(
            (
                new Mul(left.derivative(variableName), right)
            ),
            (
                new Mul(left, right.derivative(variableName))
            )
        );
    }
}
