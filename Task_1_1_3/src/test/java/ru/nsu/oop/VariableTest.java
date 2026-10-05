package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Variable;

class VariableTest {
    @ParameterizedTest
    @ValueSource(strings = {"x", "y", "velocity", "acceleration", "X", "temperature"})
    @DisplayName("Имя переменной сохраняется целиком")
    void printsFullName(String name) {
        assertEquals(name, new Variable(name).toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, 15, -25, Integer.MAX_VALUE, Integer.MIN_VALUE})
    @DisplayName("Переменная получает своё значение из таблицы")
    void readsValue(int value) {
        Expression expression = new Variable("velocity");
        assertEquals(value, expression.eval(Map.of("velocity", value, "x", 77)));
    }

    @ParameterizedTest
    @CsvSource({"x, x, 1", "x, y, 0", "velocity, velocity, 1", "velocity, x, 0", "x, X, 0"})
    @DisplayName("Производная переменной зависит от совпадения имён")
    void differentiatesByName(String name, String variableName, int expected) {
        assertEquals(expected, new Variable(name).derivative(variableName).eval(Map.of()));
    }

    @Test
    @DisplayName("Имена сравниваются по содержимому, а не по ссылкам")
    void comparesStringContents() {
        String name = new String(new char[] {'s', 'p', 'e', 'e', 'd'});
        String variableName = new String(new char[] {'s', 'p', 'e', 'e', 'd'});
        assertEquals(1, new Variable(name).derivative(variableName).eval(Map.of()));
    }

    @Test
    @DisplayName("Новое вычисление использует новую таблицу значений")
    void doesNotCachePreviousValue() {
        Expression expression = new Variable("x");
        assertEquals(10, expression.eval(Map.of("x", 10)));
        assertEquals(-3, expression.eval(Map.of("x", -3)));
        assertEquals("x", expression.toString());
    }

    @Test
    @DisplayName("Вычисление переменной без значения завершается ошибкой")
    void rejectsMissingValue() {
        // Точный тип ошибки пока не определён API. Не закрепляем NullPointerException.
        assertThrows(RuntimeException.class, () -> new Variable("x").eval(Map.of("y", 10)));
    }
}
