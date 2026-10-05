package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.oop.expression.Add;
import ru.nsu.oop.expression.Div;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Mul;
import ru.nsu.oop.expression.Number;
import ru.nsu.oop.expression.Sub;
import ru.nsu.oop.expression.Variable;
import ru.nsu.oop.parser.Parser;

/** Проверки чисел, имён из букв и выражений с полными скобками. */
class ParserTest {
    @ParameterizedTest(name = "{0} -> Number({1})")
    @CsvSource({
        "0, 0", "7, 7", "42, 42", "123456789, 123456789",
        "2147483647, 2147483647", "-1, -1", "-5, -5", "-12, -12",
        "-2147483647, -2147483647", "00, 0", "007, 7", "-0, 0", "-007, -7"
    })
    @DisplayName("Число: знак, несколько цифр, ноль и ведущие нули")
    void parsesInteger(String input, int expected) {
        Expression expression = Parser.parseInput(input);

        assertInstanceOf(Number.class, expression);
        assertEquals(expected, expression.eval(Map.of()));
        assertEquals(Integer.toString(expected), expression.toString());
    }

    @ParameterizedTest(name = "Variable({0})")
    @ValueSource(strings = {"x", "X", "y", "velocity", "alphaBeta", "xyz", "abcDEF"})
    @DisplayName("Переменная: имя целиком и сохранение регистра")
    void parsesVariable(String name) {
        Expression expression = Parser.parseInput(name);

        assertInstanceOf(Variable.class, expression);
        assertEquals(name, expression.toString());
        assertEquals(17, expression.eval(Map.of(name, 17)));
    }

    @ParameterizedTest(name = "{0} = {1}")
    @MethodSource("constantExpressions")
    @DisplayName("Все операции, отрицательные операнды и вложенные скобки")
    void parsesConstantExpression(String input, int expected,
            Class<? extends Expression> rootType) {
        Expression expression = Parser.parseInput(input);

        assertInstanceOf(rootType, expression);
        assertEquals(input, expression.toString());
        assertEquals(expected, expression.eval(Map.of()));

        Expression restored = Parser.parseInput(expression.toString());
        assertEquals(expected, restored.eval(Map.of()));
        assertEquals(expression.toString(), restored.toString());
    }

    static Stream<Arguments> constantExpressions() {
        return Stream.of(
                Arguments.of("(5+2)", 7, Add.class),
                Arguments.of("(5-2)", 3, Sub.class),
                Arguments.of("(5*2)", 10, Mul.class),
                Arguments.of("(5/2)", 2, Div.class),
                Arguments.of("(5+-2)", 3, Add.class),
                Arguments.of("(5--2)", 7, Sub.class),
                Arguments.of("(5*-2)", -10, Mul.class),
                Arguments.of("(5/-2)", -2, Div.class),
                Arguments.of("(-5+2)", -3, Add.class),
                Arguments.of("(-5-2)", -7, Sub.class),
                Arguments.of("(-5*2)", -10, Mul.class),
                Arguments.of("(-5/2)", -2, Div.class),
                Arguments.of("(-5+-2)", -7, Add.class),
                Arguments.of("(-5--2)", -3, Sub.class),
                Arguments.of("(-5*-2)", 10, Mul.class),
                Arguments.of("(-5/-2)", 2, Div.class),
                Arguments.of("(0+0)", 0, Add.class),
                Arguments.of("(0-5)", -5, Sub.class),
                Arguments.of("(0*5)", 0, Mul.class),
                Arguments.of("(0/5)", 0, Div.class),
                Arguments.of("((1+2)+(3+4))", 10, Add.class),
                Arguments.of("((9-3)-(4-1))", 3, Sub.class),
                Arguments.of("((2*3)*(4*5))", 120, Mul.class),
                Arguments.of("((24/3)/(8/4))", 4, Div.class),
                Arguments.of("(5+(2*5))", 15, Add.class),
                Arguments.of("((2+3)*(7-4))", 15, Mul.class),
                Arguments.of("(((10-2)*(3+1))/2)", 16, Div.class),
                Arguments.of("((20/(2+3))+(8-1))", 11, Add.class),
                Arguments.of("((1+2)-(3*4))", -9, Sub.class),
                Arguments.of("((8/3)+(1*2))", 4, Add.class),
                Arguments.of("(((1+2)+3)+4)", 10, Add.class),
                Arguments.of("(1+(2+(3+4)))", 10, Add.class));
    }

    @ParameterizedTest(name = "{0} = {2}")
    @MethodSource("variableExpressions")
    @DisplayName("Вычисление разобранного выражения с переменными")
    void parsesExpressionWithVariables(String input, Map<String, Integer> variables,
            int expected) {
        Expression expression = Parser.parseInput(input);

        assertEquals(input, expression.toString());
        assertEquals(expected, expression.eval(variables));
    }

    static Stream<Arguments> variableExpressions() {
        return Stream.of(
                Arguments.of("(x+y)", Map.of("x", 10, "y", 5), 15),
                Arguments.of("(velocity/2)", Map.of("velocity", 8), 4),
                Arguments.of("(3+(2*x))", Map.of("x", 10), 23),
                Arguments.of("((x*y)+(x-y))", Map.of("x", 10, "y", 5), 55),
                Arguments.of("((x*x)+(y*y))", Map.of("x", 10, "y", 5), 125),
                Arguments.of("((alpha+beta)*(gamma-delta))",
                        Map.of("alpha", 2, "beta", 3, "gamma", 10, "delta", 7), 15),
                Arguments.of("((x+1)/(x-1))", Map.of("x", 3), 2),
                Arguments.of("(x/x)", Map.of("x", 7), 1));
    }

    @Test
    @DisplayName("Разобранное дерево можно повторно вычислять и дифференцировать")
    void parsedExpressionSupportsOtherOperations() {
        Expression expression = Parser.parseInput("(3+(2*x))");

        assertEquals(23, expression.eval("x = 10; y = 13"));
        assertEquals(-5, expression.eval(Map.of("x", -4)));
        Expression derivative = expression.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", derivative.toString());
        assertEquals(2, derivative.eval(Map.of("x", 10)));
        assertEquals("(3+(2*x))", expression.toString());
    }

    @Test
    @DisplayName("Деление на ноль возникает при вычислении, а не при разборе")
    void parsesDivisionByZeroWithoutEvaluatingIt() {
        Expression expression = Parser.parseInput("(5/0)");

        assertInstanceOf(Div.class, expression);
        assertEquals("(5/0)", expression.toString());
        assertThrows(ArithmeticException.class, () -> expression.eval(Map.of()));
    }

    @ParameterizedTest(name = "Недопустимое выражение: [{0}]")
    @ValueSource(strings = {
        "", " ", "\t", "$", "@", ")", "+", "*", "/", "=",
        "(", "(+2)", "(5)", "(5%2)", "(5^2)", "(5=2)", "(5a2)", "(5",
        "(5+)", "(5-)", "(5*)", "(5/)",
        "(5+@)", "(5-@)", "(5*@)", "(5/@)",
        "(5+2]", "(5-2]", "(5*2]", "(5/2]",
        "(5+2a)", "(5-2a)", "(5*2a)", "(5/2a)",
        "-x", "2147483648", "-2147483649"
    })
    @DisplayName("Ошибки синтаксиса и числа вне диапазона int")
    void rejectsInvalidSyntax(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInput(input));
    }

    @ParameterizedTest(name = "Нет закрывающей скобки: {0}")
    @ValueSource(strings = {"(5+2", "(5-2", "(5*2", "(5/2", "(1+(2*3)"})
    @DisplayName("Конец строки не заменяет закрывающую скобку")
    void rejectsMissingClosingParenthesis(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInput(input));
    }

    @ParameterizedTest(name = "Лишний хвост: {0}")
    @ValueSource(strings = {
        "123abc", "-5abc", "x1", "(5+2)abc", "(5-2)abc", "(5*2)abc", "(5/2)abc",
        "(5+2))", "1+2", "xy+z", "123 456"
    })
    @DisplayName("Парсер обязан обработать всю строку")
    void rejectsTrailingInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInput(input));
    }

    @ParameterizedTest(name = "Некорректный знак числа: [{0}]")
    @ValueSource(strings = {"-", "--5", "-+5"})
    @DisplayName("После минуса должна находиться цифра")
    void rejectsMalformedNegativeInteger(String input) {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInput(input));
    }

    @Test
    @DisplayName("Минимальное значение int является корректным числом")
    void parsesMinimumInteger() {
        Expression expression = Parser.parseInput("-2147483648");

        assertInstanceOf(Number.class, expression);
        assertEquals(Integer.MIN_VALUE, expression.eval(Map.of()));
        assertEquals("-2147483648", expression.toString());
    }
}
