package ru.nsu.oop.expression.bricks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void calculate_returnsItsValue() {
        Number number = new Number(5);
        assertEquals(5, number.calculate(Collections.emptyMap()));
    }

    @Test
    void derivative_returnsZero() {
        Number number = new Number(5);
        Expression derivative = number.derivative("x");
        assertEquals("0", derivative.toString());
    }
}