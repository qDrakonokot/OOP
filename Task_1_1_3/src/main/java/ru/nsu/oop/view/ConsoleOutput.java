package ru.nsu.oop.view;

public class ConsoleOutput implements Output {

    @Override
    public void write(String text) {
        System.out.println(text);
    }

}
