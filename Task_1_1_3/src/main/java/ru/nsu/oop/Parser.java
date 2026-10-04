package ru.nsu.oop;

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
        Expression expression = parser.parseInput();
        if (parser.position != input.length()) {
            throw new IllegalArgumentException("Позиция " + parser.position
                    + ". Остались лишние символы!");
        }
        return expression;
    }

    private Expression parseInput() {
        if (position < this.input.length()
                && this.input.charAt(position) >= '0' && this.input.charAt(position) <= '9') {
            int start = position;
            position += 1;
            while (position < this.input.length()
                    && this.input.charAt(position) >= '0' && this.input.charAt(position) <= '9') {
                position += 1;
            }
            int finish = position;

            int number = Integer.parseInt(this.input.substring(start, finish));

            return new Number(number);
        } else if (position < this.input.length() && this.input.charAt(position) == '-') {
            final int start = position;
            position += 1;
            if (position >= this.input.length() || this.input.charAt(position) < '0'
                    || this.input.charAt(position) > '9') {
                throw new IllegalArgumentException("Позиция " + position
                        + ". После минуса ожидалась цифра!");
            }
            position += 1;
            while (position < this.input.length()
                    && this.input.charAt(position) >= '0' && this.input.charAt(position) <= '9') {
                position += 1;
            }
            int finish = position;

            int number = Integer.parseInt(this.input.substring(start, finish));

            return new Number(number);
        } else if (position < this.input.length()
                && Character.isLetter(this.input.charAt(position))) {
            int start = position;
            position += 1;
            while (position < this.input.length()
                    && Character.isLetter(this.input.charAt(position))) {
                position += 1;
            }
            int finish = position;

            String variable = this.input.substring(start, finish);

            return new Variable(variable);
        } else if (position < this.input.length() && this.input.charAt(position) == '(') {
            position += 1;

            Expression left = parseInput();
            Expression right;

            if (position < this.input.length() && this.input.charAt(position) == '+') {
                position += 1;

                right = parseInput();

                if (position >= this.input.length() || this.input.charAt(position) != ')') {
                    throw new IllegalArgumentException("Позиция " + position
                            + ". Не закрывающаяся скобка!");
                }
                position += 1; // Читаем закрывающуюся скобку

                return new Add(left, right);
            } else if (position < this.input.length() && this.input.charAt(position) == '-') {
                position += 1;

                right = parseInput();

                if (position >= this.input.length() || this.input.charAt(position) != ')') {
                    throw new IllegalArgumentException("Позиция " + position
                            + ". Не закрывающаяся скобка!");
                }
                position += 1; // Читаем закрывающуюся скобку

                return new Sub(left, right);
            } else if (position < this.input.length() && this.input.charAt(position) == '*') {
                position += 1;

                right = parseInput();

                if (position >= this.input.length() || this.input.charAt(position) != ')') {
                    throw new IllegalArgumentException("Позиция " + position
                            + ". Не закрывающаяся скобка!");
                }
                position += 1; // Читаем закрывающуюся скобку

                return new Mul(left, right);
            } else if (position < this.input.length() && this.input.charAt(position) == '/') {
                position += 1;

                right = parseInput();

                if (position >= this.input.length() || this.input.charAt(position) != ')') {
                    throw new IllegalArgumentException("Позиция " + position
                            + ". Не закрывающаяся скобка!");
                }
                position += 1; // Читаем закрывающуюся скобку

                return new Div(left, right);
            } else {
                throw new IllegalArgumentException("Позиция " + position
                        + ". Не распознана операция!");
            }
        } else {
            throw new IllegalArgumentException("Позиция " + position + " выходит за рамки строки!");
        }
    }
}
