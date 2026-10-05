package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.oop.expression.Div;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Number;
import ru.nsu.oop.expression.Sub;
import ru.nsu.oop.expression.Variable;

class DivTest {
    @ParameterizedTest
    @CsvSource({"6, 2, 3", "-6, 2, -3", "6, -2, -3", "-6, -2, 3", "0, 7, 0",
        "7, 1, 7", "5, 2, 2", "-5, 2, -2", "5, -2, -2", "2, 5, 0"})
    @DisplayName("Деление int отбрасывает дробную часть в сторону нуля")
    void evaluatesIntegerQuotient(int left, int right, int expected) {
        assertEquals(expected, new Div(new Number(left), new Number(right)).eval(Map.of()));
    }

    @Test
    @DisplayName("Частное печатается в скобках")
    void formatsQuotient() {
        assertEquals("(x/2)", new Div(new Variable("x"), new Number(2)).toString());
    }

    @Test
    @DisplayName("Вложенное деление сохраняет скобки и порядок")
    void handlesNestedQuotient() {
        Expression expression = new Div(new Number(24), new Div(new Number(6), new Number(2)));
        assertEquals("(24/(6/2))", expression.toString());
        assertEquals(8, expression.eval(Map.of()));
    }

    @Test
    @DisplayName("Создание второго частного не изменяет первое")
    void keepsInstancesIndependent() {
        Expression first = new Div(new Number(12), new Number(3));
        Expression second = new Div(new Number(30), new Number(5));
        assertEquals(4, first.eval(Map.of()));
        assertEquals(6, second.eval(Map.of()));
        assertEquals("(12/3)", first.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, 123})
    @DisplayName("Деление на нулевую константу отклоняется")
    void rejectsZeroConstantDenominator(int numerator) {
        Expression expression = new Div(new Number(numerator), new Number(0));
        assertThrows(ArithmeticException.class, () -> expression.eval(Map.of()));
    }

    @Test
    @DisplayName("Ошибка возникает и при вычислении знаменателя в ноль")
    void rejectsComputedZeroDenominator() {
        Expression expression = new Div(new Number(10),
                new Sub(new Variable("x"), new Variable("y")));
        assertThrows(ArithmeticException.class, () -> expression.eval(Map.of("x", 5, "y", 5)));
    }
}
