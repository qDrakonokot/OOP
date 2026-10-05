package ru.nsu.oop.console;

import java.util.Scanner;

/**
 * Читает математическое выражение.
 */
public class ConsoleInput {
    private final Scanner scanner;

    /** Создаёт средство чтения из стандартного потока ввода. */
    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    /**
     * Читает строку выражения и удаляет пробелы по её краям.
     *
     * @return введённая строка
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }
}
