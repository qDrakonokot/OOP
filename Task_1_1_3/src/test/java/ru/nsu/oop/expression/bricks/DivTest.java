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
}