package ru.nsu.oop;

import java.util.Scanner;
import ru.nsu.oop.expression.bricks.Expression;
import ru.nsu.oop.expression.parsers.ExpressionParser;
import ru.nsu.oop.output.ConsoleOutput;
import ru.nsu.oop.output.Output;

/**
 * Мейн.
 */
public class Main {

    /**
     * Метод мейн.
     *
     * @param args Аргументы мейна (опять не используются).
     */
    public static void main(String[] args) {

        Output output = new ConsoleOutput();

        try (Scanner scanner = new Scanner(System.in)) {

            output.write("Введите математическое выражение (например, (3+(2*x)) ): ");
            String input = scanner.nextLine();

            Expression expr = ExpressionParser.parse(input);
            output.write("Распарсенное выражение: " + expr.toString());

            output.write("Введите означивание переменных (например, x = 10) "
                + "или нажмите Enter, если их нет: ");
            String vars = scanner.nextLine();

            int result = expr.eval(vars);
            output.write("Результат вычисления: " + result);

            output.write("Введите переменную для дифференцирования (например, x): ");
            String diffVar = scanner.nextLine();

            if (!diffVar.isBlank()) {
                Expression derivative = expr.derivative(diffVar);
                output.write("Производная по '" + diffVar + "': " + derivative.toString());
            }

        } catch (IllegalArgumentException e) {
            output.write("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            output.write("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }
}