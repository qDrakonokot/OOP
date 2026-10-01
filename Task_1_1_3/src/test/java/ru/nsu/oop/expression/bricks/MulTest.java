package ru.nsu.oop.expression.bricks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void calculate_multipliesLeftAndRight() {
        // Arrange: (5 * x)
        Mul mul = new Mul(new Number(5), new Variable("x"));
        Map<String, Integer> context = Map.of("x", 4);

        // Act
        int result = mul.calculate(context);

        // Assert
        assertEquals(20, result);
    }

    @Test
    void toString_formatsCorrectly() {
        // Arrange
        Mul mul = new Mul(new Number(5), new Variable("x"));

        // Act & Assert
        assertEquals("(5*x)", mul.toString());
    }

    @Test
    void derivative_appliesProductRule() {
        // Arrange: (x * 5)
        Mul mul = new Mul(new Variable("x"), new Number(5));

        // Act
        Expression derivative = mul.derivative("x");

        // Assert: правило Лейбница (u'v + uv') -> ((1*5)+(x*0))
        assertEquals("((1*5)+(x*0))", derivative.toString());
    }
}