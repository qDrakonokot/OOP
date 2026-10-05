package ru.nsu.oop.parser;

import ru.nsu.oop.expression.Add;
import ru.nsu.oop.expression.Div;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.expression.Mul;
import ru.nsu.oop.expression.Number;
import ru.nsu.oop.expression.Sub;
import ru.nsu.oop.expression.Variable;

/** Разбирает выражения с целыми числами, переменными и операциями в скобках. */
public class Parser {
    private final String input;
    private int position;

    private Parser(String input) {
        this.input = input;
        this.position = 0;
    }

    /**
     * Разбирает всю строку и создаёт дерево выражения.
     *
     * @param input целое число, имя переменной или бинарное выражение в скобках
     * @return корень дерева выражения
     * @throws IllegalArgumentException если строка содержит некорректное выражение
     */
    public static Expression parseInput(String input) {
        Parser parser = new Parser(input);
        Expression expression = parser.parseExpression();
        if (parser.hasNext()) {
            throw parser.error("Остались лишние символы!");
        }
        return expression;
    }

    private Expression parseExpression() {
        return switch (peek()) {
            case '(' -> parseBinaryExpression();
            case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> parseNumber();
            default -> {
                if (Character.isLetter(peek())) {
                    yield parseVariable();
                }
                throw error("Ожидалось число, переменная или выражение в скобках!");
            }
        };
    }

    private Expression parseNumber() {
        final int start = position;
        if (peek() == '-') {
            read();
            if (!hasNext() || !isDigit(peek())) {
                throw error("После минуса ожидалась цифра!");
            }
        }
        while (hasNext() && isDigit(peek())) {
            read();
        }
        int number = Integer.parseInt(input.substring(start, position));
        return new Number(number);
    }

    private Expression parseVariable() {
        int start = position;
        while (hasNext() && Character.isLetter(peek())) {
            read();
        }
        return new Variable(input.substring(start, position));
    }

    private Expression parseBinaryExpression() {
        expect('(');
        Expression left = parseExpression();
        char operation = read();
        Expression right = parseExpression();
        expect(')');
        return createOperation(operation, left, right);
    }

    private Expression createOperation(char operation, Expression left, Expression right) {
        return switch (operation) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw error("Не распознана операция '" + operation + "'!");
        };
    }

    private boolean hasNext() {
        return position < input.length();
    }

    private char peek() {
        if (!hasNext()) {
            throw error("Неожиданный конец строки!");
        }
        return input.charAt(position);
    }

    private char read() {
        char symbol = peek();
        position += 1;
        return symbol;
    }

    private void expect(char expected) {
        if (peek() != expected) {
            throw error("Ожидался символ '" + expected + "'!");
        }
        read();
    }

    private boolean isDigit(char symbol) {
        return symbol >= '0' && symbol <= '9';
    }

    private IllegalArgumentException error(String message) {
        return new IllegalArgumentException("Позиция " + position + ". " + message);
    }
}
