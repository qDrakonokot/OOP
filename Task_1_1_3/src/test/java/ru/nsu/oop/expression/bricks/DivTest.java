package ru.nsu.oop.expression.bricks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void derivative_appliesQuotientRule() {
        // Выражение: (x/5)
        Div div = new Div(new Variable("x"), new Number(5));

        // Производная: (((1*5)-(x*0))/(5*5))
        Expression derivative = div.derivative("x");
        assertEquals("(((1*5)-(x*0))/(5*5))", derivative.toString());
    }

    @Test
    void calculate_dividesLeftByRight() {
        Div div = new Div(new Number(20), new Number(4));
        assertEquals(5, div.calculate(java.util.Collections.emptyMap()));
    }

    @Test
    void toString_formatsCorrectly() {
        Div div = new Div(new Variable("x"), new Number(5));
        assertEquals("(x/5)", div.toString());
    }
}