package ru.nsu.oop;

import ru.nsu.oop.console.ConsoleInput;
import ru.nsu.oop.console.ConsoleView;
import ru.nsu.oop.expression.Expression;
import ru.nsu.oop.parser.Parser;

/** Читает выражение и значения переменных, затем выводит результат вычисления. */
public class Main {
    /**
     * Запускает ввод и вычисление выражения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        String command = input.readCommand();
        String assignments = input.readCommand();
        Expression expression = Parser.parseInput(command);
        ConsoleView view = new ConsoleView();
        view.printResult(expression.eval(assignments));
    }
}
