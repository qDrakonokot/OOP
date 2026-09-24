package ru.nsu.oop.expression.bricks;

import java.util.Map;

public final class Add extends Expression {

    private final Expression left;
    private final Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    protected int calculate(Map<String, Integer> variablesValues) {
        return left.calculate(variablesValues) + right.calculate(variablesValues);
    }

    @Override
    protected String asString() {
        return "(" + left.asString() + "+" + right.asString() + ")";
    }

    @Override
    public Expression derivative(String variableName) {
        return new Add(left.derivative(variableName), right.derivative(variableName));
    }

}
