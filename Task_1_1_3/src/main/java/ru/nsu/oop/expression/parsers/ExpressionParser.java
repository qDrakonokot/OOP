package ru.nsu.oop.expression.parsers;

import ru.nsu.oop.expression.bricks.Add;
import ru.nsu.oop.expression.bricks.Div;
import ru.nsu.oop.expression.bricks.Expression;
import ru.nsu.oop.expression.bricks.Mul;
import ru.nsu.oop.expression.bricks.Number;
import ru.nsu.oop.expression.bricks.Sub;
import ru.nsu.oop.expression.bricks.Variable;

/**
 * Класс реализующий парсер для выражения.
 */
public final class ExpressionParser {

    private static final char BRACKET_OPEN = '(';
    private static final char BRACKET_CLOSE = ')';
    private static final char OP_ADD = '+';
    private static final char OP_SUB = '-';
    private static final char OP_MUL = '*';
    private static final char OP_DIV = '/';


    private ExpressionParser() {

    }

    /**
     * Метод парсинга выражения.
     *
     * @param input строка представляющая выражение.
     * @return AST для этого выражения.
     */
    public static Expression parse(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }

        String cleanInput = input.replace(" ", "");
        return parseInternal(cleanInput);
    }

    private static Expression parseInternal(String expression) {

        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Encountered empty expression during parsing");
        }

        if (expression.charAt(0) != BRACKET_OPEN) {
            return parseLeaf(expression);
        }

        if (expression.charAt(expression.length() - 1) != BRACKET_CLOSE) {
            throw new IllegalArgumentException(
                "Missing closing bracket in expression: " + expression);
        }

        String innerExpression = expression.substring(1, expression.length() - 1);

        int operatorIndex = findMainOperatorIndex(innerExpression);
        if (operatorIndex == -1) {
            throw new IllegalArgumentException(
                "Invalid expression format (no main operator found): " + expression);
        }

        char operator = innerExpression.charAt(operatorIndex);
        String leftPart = innerExpression.substring(0, operatorIndex);
        String rightPart = innerExpression.substring(operatorIndex + 1);

        Expression leftNode = parseInternal(leftPart);
        Expression rightNode = parseInternal(rightPart);

        return createOperationNode(operator, leftNode, rightNode);
    }

    private static int findMainOperatorIndex(String expression) {
        int bracketBalance = 0;

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (currentChar == BRACKET_OPEN) {
                bracketBalance++;
            } else if (currentChar == BRACKET_CLOSE) {
                bracketBalance--;
            } else if (bracketBalance == 0 && isOperator(currentChar)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isOperator(char c) {
        return c == OP_ADD || c == OP_SUB || c == OP_MUL || c == OP_DIV;
    }

    private static Expression parseLeaf(String leaf) {
        try {
            int value = Integer.parseInt(leaf);
            return new Number(value);
        } catch (NumberFormatException e) {
            return new Variable(leaf);
        }
    }

    private static Expression createOperationNode(
        char operator,
        Expression left,
        Expression right
    ) {
        return switch (operator) {
            case OP_ADD -> new Add(left, right);
            case OP_SUB -> new Sub(left, right);
            case OP_MUL -> new Mul(left, right);
            case OP_DIV -> new Div(left, right);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}