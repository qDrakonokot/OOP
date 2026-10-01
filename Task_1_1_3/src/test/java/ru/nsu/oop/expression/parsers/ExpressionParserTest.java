package ru.nsu.oop.expression.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.nsu.oop.expression.bricks.Expression;

class ExpressionParserTest {

    @Test
    void parse_complexExpression_buildsCorrectTree() {
        // Arrange
        String input = "(3+(2*x))";

        // Act
        Expression expr = ExpressionParser.parse(input);

        // Assert
        // Самый надежный способ проверить структуру дерева - проверить его toString()
        assertEquals("(3+(2*x))", expr.toString());
    }

    @Test
    void parse_leafNumber_buildsNumberNode() {
        Expression expr = ExpressionParser.parse("42");
        assertEquals("42", expr.toString());
    }

    @Test
    void parse_invalidBrackets_throwsException() {
        String input = "(3+2"; // Нет закрывающей скобки
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse(input));
    }

    @Test
    void parse_nullOrEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse(null));
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("   "));
    }

    @Test
    void parse_unknownOperator_throwsException() {
        // Оператор ^ мы не поддерживаем
        String input = "(3^2)";
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse(input));
    }

    @Test
    void parse_noMainOperator_throwsException() {
        // Внутри скобок нет оператора
        String input = "(42)";
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse(input));
    }

    @Test
    void parse_emptySubExpression_throwsException() {
        // После плюса ничего нет
        String input = "(3+)";
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse(input));
    }
}