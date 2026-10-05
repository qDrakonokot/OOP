package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.oop.expression.Add;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Mul;
import ru.nsu.oop.expression.Number;
import ru.nsu.oop.expression.Variable;

class MulTest {
    @ParameterizedTest
    @CsvSource({"0, 0, 0", "3, 2, 6", "-3, 2, -6", "3, -2, -6", "-3, -2, 6",
        "0, 7, 0", "7, 0, 0", "1, -7, -7", "-1, -7, 7"})
    @DisplayName("Умножение учитывает знаки, ноль и единицу")
    void evaluatesProduct(int left, int right, int expected) {
        assertEquals(expected, new Mul(new Number(left), new Number(right)).eval(Map.of()));
    }

    @Test
    @DisplayName("Произведение печатается в скобках")
    void formatsProduct() {
        assertEquals("(2*x)", new Mul(new Number(2), new Variable("x")).toString());
    }

    @Test
    @DisplayName("Произведение вычисляет вложенные выражения")
    void handlesNestedProduct() {
        Expression expression = new Mul(new Add(new Number(2), new Number(3)), new Number(4));
        assertEquals("((2+3)*4)", expression.toString());
        assertEquals(20, expression.eval(Map.of()));
    }

    @Test
    @DisplayName("Создание второго произведения не изменяет первое")
    void keepsInstancesIndependent() {
        Expression first = new Mul(new Number(2), new Number(3));
        Expression second = new Mul(new Number(4), new Number(5));
        assertEquals(6, first.eval(Map.of()));
        assertEquals(20, second.eval(Map.of()));
        assertEquals("(2*3)", first.toString());
    }

    @Test
    @DisplayName("Произведение переменных использует переданные значения")
    void evaluatesVariables() {
        Expression expression = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(-30, expression.eval(Map.of("x", 10, "y", -3)));
    }
}
