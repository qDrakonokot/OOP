package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.oop.expression.Add;
import ru.nsu.oop.expression.Div;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Mul;
import ru.nsu.oop.expression.Number;
import ru.nsu.oop.expression.Sub;
import ru.nsu.oop.expression.Variable;

class ExpressionTest {
    @ParameterizedTest
    @MethodSource("assignmentCases")
    @DisplayName("Строка значений поддерживает пробелы, знаки и произвольный порядок")
    void evaluatesAssignmentString(String assignments, int expected) {
        Expression expression = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals(expected, expression.eval(assignments));
    }

    static Stream<Arguments> assignmentCases() {
        return Stream.of(
                Arguments.of("x = 10; y = 13", 23),
                Arguments.of("x=10", 23),
                Arguments.of("  x  =  10  ;  y  =  13  ", 23),
                Arguments.of("y=13;x=10", 23),
                Arguments.of("x=-4;y=0", -5),
                Arguments.of("x=0", 3),
                Arguments.of("x=+4", 11),
                Arguments.of("\tx\t=\t10\n; y=13", 23)
        );
    }

    @Test
    @DisplayName("Многобуквенные имена читаются из строки присваиваний")
    void evaluatesMultiLetterVariables() {
        Expression expression = new Add(new Variable("velocity"), new Variable("acceleration"));
        assertEquals(7, expression.eval("acceleration=-3; velocity=10"));
    }

    @Test
    @DisplayName("Строковое вычисление константы допускает лишние присваивания")
    void evaluatesConstantWithUnusedAssignments() {
        assertEquals(17, new Number(17).eval("x=4;y=-2"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", " \t\n "})
    @DisplayName("Константы вычисляются с пустым означиванием или одними пробелами")
    void evaluatesConstantsWithEmptyAssignments(String assignments) {
        Expression expression = new Add(new Number(5), new Number(2));
        assertEquals(5, new Number(5).eval(assignments));
        assertEquals(7, expression.eval(assignments));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", " \t\n "})
    @DisplayName("Пустое означивание не задаёт значение переменной")
    void rejectsEmptyAssignmentsWhenVariableIsRequired(String assignments) {
        assertThrows(RuntimeException.class, () -> new Variable("x").eval(assignments));
    }

    @Test
    @DisplayName("Все четыре операции работают в одном выражении")
    void evaluatesMixedExpression() {
        Expression expression = new Div(
                new Mul(new Add(new Variable("x"), new Number(3)),
                        new Sub(new Variable("y"), new Number(2))),
                new Add(new Variable("z"), new Number(1)));
        assertEquals("(((x+3)*(y-2))/(z+1))", expression.toString());
        assertEquals(10, expression.eval("x=5;y=7;z=3"));
        assertEquals(10, expression.eval(Map.of("x", 5, "y", 7, "z", 3)));
    }

    @Test
    @DisplayName("Вычисление не меняет ни таблицу, ни выражение")
    void leavesInputsUnchanged() {
        Map<String, Integer> variables = new HashMap<>(Map.of("x", 7, "y", -2));
        Map<String, Integer> snapshot = new HashMap<>(variables);
        Expression expression = new Mul(new Add(new Variable("x"), new Number(3)),
                new Sub(new Variable("y"), new Number(2)));
        String before = expression.toString();
        assertEquals(-40, expression.eval(variables));
        assertEquals(snapshot, variables);
        assertEquals(before, expression.toString());
        assertEquals(-40, expression.eval(Map.copyOf(variables)));
    }

    @Test
    @DisplayName("Одно выражение можно вычислять с разными строками значений")
    void reevaluatesWithNewAssignments() {
        Expression expression = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals(23, expression.eval("x=10"));
        assertEquals(-5, expression.eval("x=-4"));
        assertEquals(3, expression.eval("x=0"));
        assertEquals("(3+(2*x))", expression.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"x=abc", "x=1.5", "x=2147483648", "x=-2147483649"})
    @DisplayName("Нечисловое значение или выход за диапазон int отклоняется")
    void rejectsInvalidIntegerValues(String assignments) {
        assertThrows(IllegalArgumentException.class, () -> new Variable("x").eval(assignments));
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "x=", "x=2;y"})
    @DisplayName("Неполное присваивание не используется для вычисления")
    void rejectsIncompleteAssignments(String assignments) {
        // Пока API не определяет точный тип ошибки для неправильного формата.
        assertThrows(RuntimeException.class, () -> new Variable("x").eval(assignments));
    }

    @Test
    @DisplayName("Строка без значения нужной переменной завершается ошибкой")
    void rejectsMissingVariableInString() {
        Expression expression = new Add(new Variable("x"), new Variable("y"));
        assertThrows(RuntimeException.class, () -> expression.eval("x=2"));
    }

    @ParameterizedTest
    @MethodSource("printCases")
    @ResourceLock(Resources.SYSTEM_OUT)
    @DisplayName("print выводит выражение и перевод строки")
    void printsExpression(Expression expression, String expected) {
        assertEquals(expected + System.lineSeparator(),
                TestConsole.captureOutput(expression::print));
    }

    static Stream<Arguments> printCases() {
        return Stream.of(
                Arguments.of(new Number(5), "5"),
                Arguments.of(new Number(-5), "-5"),
                Arguments.of(new Variable("velocity"), "velocity"),
                Arguments.of(new Add(new Number(1), new Number(2)), "(1+2)"),
                Arguments.of(new Sub(new Number(1), new Number(2)), "(1-2)"),
                Arguments.of(new Mul(new Number(1), new Number(2)), "(1*2)"),
                Arguments.of(new Div(new Number(1), new Number(2)), "(1/2)"),
                Arguments.of(new Add(new Number(3), new Mul(new Number(2),
                        new Variable("x"))), "(3+(2*x))")
        );
    }
}
