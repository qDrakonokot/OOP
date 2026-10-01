package ru.nsu.oop.output;

/**
 * Класс реализующий интерфейс вывода в консоль.
 */
public class ConsoleOutput implements Output {

    @Override
    public void write(String text) {
        System.out.println(text);
    }

}
