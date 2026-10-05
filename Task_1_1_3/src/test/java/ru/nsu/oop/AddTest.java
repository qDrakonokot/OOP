package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.oop.expression.Add;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Number;
import ru.nsu.oop.expression.Variable;

class AddTest {
    @ParameterizedTest
    @CsvSource({"0, 0, 0", "3, 2, 5", "-3, 2, -1", "3, -2, 1", "-3, -2, -5",
        "0, 7, 7", "7, 0, 7", "1000, -1000, 0"})
    @DisplayName("Сложение положительных, отрицательных чисел и нуля")
    void evaluatesSum(int left, int right, int expected) {
        assertEquals(expected, new Add(new Number(left), new Number(right)).eval(Map.of()));
    }

    @Test
    @DisplayName("Сумма печатается в скобках")
    void formatsSum() {
        assertEquals("(3+x)", new Add(new Number(3), new Variable("x")).toString());
    }

    @Test
    @DisplayName("Вложенные суммы вычисляются и печатаются рекурсивно")
    void handlesNestedSum() {
        Expression expression = new Add(new Number(1), new Add(new Number(3), new Number(2)));
        assertEquals("(1+(3+2))", expression.toString());
        assertEquals(6, expression.eval(Map.of()));
    }

    @Test
    @DisplayName("Создание второй суммы не изменяет первую")
    void keepsInstancesIndependent() {
        Expression first = new Add(new Number(2), new Number(3));
        Expression second = new Add(new Number(10), new Number(20));
        assertEquals(5, first.eval(Map.of()));
        assertEquals(30, second.eval(Map.of()));
        assertEquals("(2+3)", first.toString());
    }

    @Test
    @DisplayName("Сумма двух переменных получает значения из одной таблицы")
    void evaluatesVariables() {
        Expression expression = new Add(new Variable("x"), new Variable("y"));
        assertEquals(7, expression.eval(Map.of("x", 10, "y", -3)));
    }
}
