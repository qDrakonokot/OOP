package ru.nsu.oop.expression;

import java.util.Map;

/** Представляет произведение двух выражений. */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создаёт произведение двух выражений.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + "*" + right + ")";
    }

    @Override
    public Expression derivative(String variableName) {
        return new Add(
                new Mul(left.derivative(variableName), right),
                new Mul(left, right.derivative(variableName)));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }
}
