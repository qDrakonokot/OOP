package ru.nsu.oop.view;

import ru.nsu.oop.model.Hand;

import java.util.Scanner;

/**
 * Реализация GameView для работы с системной консолью через стандартные потоки (System.in /
 * System.out).
 */
public class ConsoleView implements GameView {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showCards(String ownerName, Hand hand) {
        System.out.println(ownerName + ": " + hand);
    }

    @Override
    public boolean askPlayerMove() {
        System.out.println("Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться");
        return sc.nextInt() == 1;
    }

    @Override
    public boolean askPlayAgain() {
        System.out.println("\nХотите сыграть еще? (1 - да, 0 - выход)");
        return sc.nextInt() == 1;
    }
}