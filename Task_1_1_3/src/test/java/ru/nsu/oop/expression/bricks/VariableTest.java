package ru.nsu.oop.expression.bricks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void calculate_variableExists_returnsValue() {
        Variable var = new Variable("x");
        Map<String, Integer> context = Map.of("x", 42);

        assertEquals(42, var.calculate(context));
    }

    @Test
    void calculate_variableMissing_throwsException() {
        Variable var = new Variable("x");
        Map<String, Integer> context = Map.of("y", 10); // x отсутствует

        assertThrows(IllegalArgumentException.class, () -> var.calculate(context));
    }

    @Test
    void derivative_sameVariable_returnsOne() {
        Variable var = new Variable("x");
        assertEquals("1", var.derivative("x").toString());
    }

    @Test
    void derivative_differentVariable_returnsZero() {
        Variable var = new Variable("x");
        assertEquals("0", var.derivative("y").toString());
    }
}