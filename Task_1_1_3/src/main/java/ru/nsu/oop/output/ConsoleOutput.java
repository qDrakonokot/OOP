package ru.nsu.oop.output;

public class ConsoleOutput implements Output {

    @Override
    public void write(String text) {
        System.out.println(text);
    }

}
