package ru.nsu.oop.view;

import ru.nsu.oop.model.Hand;

public interface GameView {

    void showMessage(String message);

    void showCards(String ownerName, Hand hand);

    boolean askPlayerMove();

    boolean askPlayAgain();
}
