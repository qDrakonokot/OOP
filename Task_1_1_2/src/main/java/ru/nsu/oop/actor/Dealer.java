package ru.nsu.oop.actor;

import ru.nsu.oop.view.GameView;

public class Dealer extends Participant {

    public Dealer() {
        super("Дилер");
    }

    @Override
    public boolean makeDecision(GameView view) {
        return getScore() < 17;
    }
}
