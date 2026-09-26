package ru.nsu.oop.expression.bricks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void calculate_addsLeftAndRight() {
        Add add = new Add(new Number(5), new Variable("x"));
        Map<String, Integer> context = Map.of("x", 10);

        assertEquals(15, add.calculate(context));
    }

    @Test
    void derivative_returnsSumOfDerivatives() {
        // Выражение: (5+x)
        Add add = new Add(new Number(5), new Variable("x"));

        // Производная по x: (0+1)
        Expression derivative = add.derivative("x");
        assertEquals("(0+1)", derivative.toString());
    }
}