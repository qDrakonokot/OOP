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
}