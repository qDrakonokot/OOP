package ru.nsu.oop.expression;

import java.util.Map;

/** Представляет разность двух выражений. */
public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создаёт разность двух выражений.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + "-" + right + ")";
    }

    @Override
    public Expression derivative(String variableName) {
        return new Sub(left.derivative(variableName), right.derivative(variableName));
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) - right.eval(variables);
    }
}
