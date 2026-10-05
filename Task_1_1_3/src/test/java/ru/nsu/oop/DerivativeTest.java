package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.oop.expression.Add;
import ru.nsu.oop.expression.Div;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Mul;
import ru.nsu.oop.expression.Number;
import ru.nsu.oop.expression.Sub;
import ru.nsu.oop.expression.Variable;

class DerivativeTest {
    @ParameterizedTest
    @MethodSource("derivativeValueCases")
    @DisplayName("Значение производной соответствует математическому правилу")
    void evaluatesDerivative(Expression expression, String variableName,
                             Map<String, Integer> variables, int expected) {
        assertEquals(expected, expression.derivative(variableName).eval(variables));
    }

    static Stream<Arguments> derivativeValueCases() {
        Expression x = new Variable("x");
        Expression y = new Variable("y");
        Map<String, Integer> point = Map.of("x", 4, "y", -3);
        return Stream.of(
                Arguments.of(new Add(x, y), "x", point, 1),
                Arguments.of(new Add(x, y), "y", point, 1),
                Arguments.of(new Add(x, y), "z", point, 0),
                Arguments.of(new Sub(x, y), "x", point, 1),
                Arguments.of(new Sub(x, y), "y", point, -1),
                Arguments.of(new Sub(x, y), "z", point, 0),
                Arguments.of(new Sub(new Number(7), x), "x", point, -1),
                Arguments.of(new Mul(x, y), "x", point, -3),
                Arguments.of(new Mul(x, y), "y", point, 4),
                Arguments.of(new Mul(x, y), "z", point, 0),
                Arguments.of(new Mul(x, x), "x", point, 8),
                Arguments.of(new Mul(new Number(2), x), "x", point, 2),
                Arguments.of(new Mul(new Number(0), x), "x", point, 0),
                Arguments.of(new Div(x, new Number(1)), "x", point, 1),
                Arguments.of(new Div(new Number(0), new Number(2)), "x", point, 0),
                Arguments.of(new Div(new Number(8), x), "x", Map.of("x", 1), -8),
                Arguments.of(new Div(new Number(8), x), "x", Map.of("x", 2), -2),
                Arguments.of(new Div(x, y), "x", Map.of("x", 12, "y", 1), 1),
                Arguments.of(new Div(x, y), "y", Map.of("x", 12, "y", 2), -3),
                Arguments.of(new Div(x, y), "z", Map.of("x", 12, "y", 2), 0),
                Arguments.of(new Div(new Mul(x, x), x), "x", Map.of("x", 3), 1),
                Arguments.of(new Mul(new Add(x, new Number(3)),
                        new Sub(x, new Number(1))), "x", Map.of("x", 4), 10),
                Arguments.of(new Add(new Number(3), new Mul(new Number(2), x)), "x", point, 2),
                Arguments.of(new Mul(new Variable("velocity"), new Variable("velocity")),
                        "velocity", Map.of("velocity", 5), 10),
                Arguments.of(new Div(new Number(6), new Number(2)), "x", Map.of(), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("derivativeTextCases")
    @DisplayName("Производная печатается как новое выражение без обязательного упрощения")
    void formatsDerivative(Expression expression, String expected) {
        assertEquals(expected, expression.derivative("x").toString());
    }

    static Stream<Arguments> derivativeTextCases() {
        return Stream.of(
                Arguments.of(new Number(7), "0"),
                Arguments.of(new Variable("x"), "1"),
                Arguments.of(new Variable("y"), "0"),
                Arguments.of(new Add(new Variable("x"), new Number(5)), "(1+0)"),
                Arguments.of(new Sub(new Variable("x"), new Number(5)), "(1-0)"),
                Arguments.of(new Mul(new Number(2), new Variable("x")), "((0*x)+(2*1))"),
                Arguments.of(new Div(new Variable("x"), new Number(2)),
                        "(((1*2)-(x*0))/(2*2))"),
                Arguments.of(new Add(new Number(3), new Mul(new Number(2),
                        new Variable("x"))), "(0+((0*x)+(2*1)))")
        );
    }

    @ParameterizedTest
    @MethodSource("originalExpressionCases")
    @DisplayName("Дифференцирование сохраняет исходное выражение и создаёт новый корень")
    void leavesOriginalUnchanged(Expression original, int expectedValue) {
        final Map<String, Integer> variables = Map.of("x", 4, "y", 2);
        final String before = original.toString();
        Expression firstDerivative = original.derivative("x");
        Expression secondDerivative = original.derivative("y");
        assertNotSame(original, firstDerivative);
        assertNotSame(original, secondDerivative);
        assertNotSame(firstDerivative, secondDerivative);
        firstDerivative.eval(variables);
        secondDerivative.eval(variables);
        assertEquals(before, original.toString());
        assertEquals(expectedValue, original.eval(variables));
    }

    static Stream<Arguments> originalExpressionCases() {
        return Stream.of(
                Arguments.of(new Number(5), 5),
                Arguments.of(new Variable("x"), 4),
                Arguments.of(new Add(new Variable("x"), new Variable("y")), 6),
                Arguments.of(new Sub(new Variable("x"), new Variable("y")), 2),
                Arguments.of(new Mul(new Variable("x"), new Variable("y")), 8),
                Arguments.of(new Div(new Variable("x"), new Variable("y")), 2),
                Arguments.of(new Add(new Number(3), new Mul(new Number(2),
                        new Variable("x"))), 11)
        );
    }

    @Test
    @DisplayName("Можно вычислить вторую и третью производные многочлена")
    void differentiatesPolynomialRepeatedly() {
        Expression x = new Variable("x");
        Expression polynomial = new Add(new Add(new Mul(x, x),
                new Mul(new Number(3), x)), new Number(7));
        Expression first = polynomial.derivative("x");
        Expression second = first.derivative("x");
        final Expression third = second.derivative("x");
        assertEquals(35, polynomial.eval(Map.of("x", 4)));
        assertEquals(11, first.eval(Map.of("x", 4)));
        assertEquals(2, second.eval(Map.of("x", 4)));
        assertEquals(0, third.eval(Map.of("x", 4)));
        assertEquals(-3, first.eval(Map.of("x", -3)));
        assertEquals(2, second.eval(Map.of("x", -3)));
    }
}
