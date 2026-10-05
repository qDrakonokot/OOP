package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Number;
import ru.nsu.oop.expression.Sub;
import ru.nsu.oop.expression.Variable;

class SubTest {
    @ParameterizedTest
    @CsvSource({"0, 0, 0", "7, 2, 5", "2, 7, -5", "-3, 2, -5", "3, -2, 5",
        "-3, -2, -1", "0, 7, -7", "7, 0, 7"})
    @DisplayName("Вычитание сохраняет порядок операндов")
    void evaluatesDifference(int left, int right, int expected) {
        assertEquals(expected, new Sub(new Number(left), new Number(right)).eval(Map.of()));
    }

    @Test
    @DisplayName("Разность печатается в скобках")
    void formatsDifference() {
        assertEquals("(x-3)", new Sub(new Variable("x"), new Number(3)).toString());
    }

    @Test
    @DisplayName("Скобки сохраняют порядок вложенного вычитания")
    void handlesNestedDifference() {
        Expression expression = new Sub(new Number(10), new Sub(new Number(5), new Number(2)));
        assertEquals("(10-(5-2))", expression.toString());
        assertEquals(7, expression.eval(Map.of()));
    }

    @Test
    @DisplayName("Создание второй разности не изменяет первую")
    void keepsInstancesIndependent() {
        Expression first = new Sub(new Number(10), new Number(3));
        Expression second = new Sub(new Number(1), new Number(8));
        assertEquals(7, first.eval(Map.of()));
        assertEquals(-7, second.eval(Map.of()));
        assertEquals("(10-3)", first.toString());
    }

    @Test
    @DisplayName("Разность переменных использует переданные значения")
    void evaluatesVariables() {
        Expression expression = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(13, expression.eval(Map.of("x", 10, "y", -3)));
    }
}
