package ru.nsu.oop.view;

import java.util.Scanner;
import ru.nsu.oop.actor.Participant;
import ru.nsu.oop.model.Card;

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
    public boolean askPlayerMove() {
        System.out.println("Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться");
        return sc.nextInt() == 1;
    }

    @Override
    public boolean askPlayAgain() {
        System.out.println("\nХотите сыграть еще? (1 - да, 0 - выход)");
        return sc.nextInt() == 1;
    }

    @Override
    public void showCards(Participant participant) {
        System.out.println("Карты (" + participant.getName() + "): " + participant.getHand());
    }

    @Override
    public void showDealerHiddenCard(Card openCard) {
        System.out.println("Карты дилера: [" + openCard + ", <закрытая карта>]");
    }

    @Override
    public void showCardDrawn(Participant participant, Card card) {
        System.out.println(participant.getName() + " открывает карту: " + card);
    }
}