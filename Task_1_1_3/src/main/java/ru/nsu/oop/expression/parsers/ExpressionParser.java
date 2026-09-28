package ru.nsu.oop.expression.parsers;

import java.util.Set;
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

    private static final String BRACKET_OPEN = "(";
    private static final String BRACKET_CLOSE = ")";
    private static final String OP_ADD = "+";
    private static final String OP_SUB = "-";
    private static final String OP_MUL = "*";
    private static final String OP_DIV = "/";

    private static final Set<String> OPERATORS = Set.of(OP_ADD, OP_SUB, OP_MUL, OP_DIV);

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

        if (!expression.startsWith(BRACKET_OPEN)) {
            return parseLeaf(expression);
        }

        if (!expression.endsWith(BRACKET_CLOSE)) {
            throw new IllegalArgumentException(
                "Missing closing bracket in expression: " + expression);
        }

        String innerExpression = expression.substring(BRACKET_OPEN.length(),
            expression.length() - BRACKET_CLOSE.length());

        int operatorIndex = findMainOperatorIndex(innerExpression);
        if (operatorIndex == -1) {
            throw new IllegalArgumentException(
                "Invalid expression format (no main operator found): " + expression);
        }

        String operator = innerExpression.substring(operatorIndex, operatorIndex + 1);
        String leftPart = innerExpression.substring(0, operatorIndex);
        String rightPart = innerExpression.substring(operatorIndex + 1);

        Expression leftNode = parseInternal(leftPart);
        Expression rightNode = parseInternal(rightPart);

        return createOperationNode(operator, leftNode, rightNode);
    }

    private static int findMainOperatorIndex(String expression) {
        int bracketBalance = 0;

        for (int i = 0; i < expression.length(); i++) {
            String currentSymbol = expression.substring(i, i + 1);

            if (currentSymbol.equals(BRACKET_OPEN)) {
                bracketBalance++;
            } else if (currentSymbol.equals(BRACKET_CLOSE)) {
                bracketBalance--;
            } else if (bracketBalance == 0 && isOperator(currentSymbol)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isOperator(String symbol) {
        return OPERATORS.contains(symbol);
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
        String operator,
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