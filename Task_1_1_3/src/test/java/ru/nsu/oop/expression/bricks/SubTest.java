package ru.nsu.oop.expression.bricks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void calculate_subtractsRightFromLeft() {
        // Arrange: (10 - x)
        Sub sub = new Sub(new Number(10), new Variable("x"));
        Map<String, Integer> context = Map.of("x", 3);

        // Act
        int result = sub.calculate(context);

        // Assert
        assertEquals(7, result);
    }

    @Test
    void toString_formatsCorrectly() {
        // Arrange
        Sub sub = new Sub(new Variable("x"), new Number(5));

        // Act & Assert
        assertEquals("(x-5)", sub.toString());
    }

    @Test
    void derivative_returnsDifferenceOfDerivatives() {
        // Arrange: (x - 5)
        Sub sub = new Sub(new Variable("x"), new Number(5));

        // Act
        Expression derivative = sub.derivative("x");

        // Assert: (x' - 5') -> (1 - 0)
        assertEquals("(1-0)", derivative.toString());
    }
}