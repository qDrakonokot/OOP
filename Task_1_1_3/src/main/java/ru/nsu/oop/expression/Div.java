package ru.nsu.oop.expression;

import java.util.Map;

/** Представляет частное двух выражений. */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создаёт частное двух выражений.
     *
     * @param left делимое
     * @param right делитель
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + "/" + right + ")";
    }

    @Override
    public Expression derivative(String variableName) {
        return new Div(
                new Sub(
                        new Mul(
                                left.derivative(variableName),
                                right),
                        new Mul(
                                left,
                                right.derivative(variableName)
                        )
                ),
                new Mul(right, right));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) / right.eval(variables);
    }
}
