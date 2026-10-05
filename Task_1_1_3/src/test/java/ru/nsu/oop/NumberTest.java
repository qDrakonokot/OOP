package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Number;

class NumberTest {
    @ParameterizedTest
    @CsvSource({
        "0, 0", "1, 1", "-1, -1", "42, 42", "-125, -125",
        "2147483647, 2147483647", "-2147483648, -2147483648"
    })
    @DisplayName("Число печатается без скобок, включая границы int")
    void formatsInteger(int value, String expected) {
        assertEquals(expected, new ru.nsu.oop.expression.Number(value).toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, 42, -125, Integer.MAX_VALUE, Integer.MIN_VALUE})
    @DisplayName("Значение константы не зависит от таблицы переменных")
    void evaluatesWithoutVariables(int value) {
        Expression expression = new ru.nsu.oop.expression.Number(value);
        assertEquals(value, expression.eval(Map.of()));
        assertEquals(value, expression.eval(Map.of("x", 999)));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, 42, -125, Integer.MAX_VALUE, Integer.MIN_VALUE})
    @DisplayName("Производная константы — новое выражение со значением ноль")
    void differentiatesToZero(int value) {
        Expression original = new Number(value);
        Expression derivative = original.derivative("velocity");
        assertNotSame(original, derivative);
        assertEquals("0", derivative.toString());
        assertEquals(0, derivative.eval(Map.of()));
        assertEquals(value, original.eval(Map.of()));
    }
}
