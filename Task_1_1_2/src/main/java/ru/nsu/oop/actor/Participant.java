package ru.nsu.oop.actor;

import ru.nsu.oop.model.Card;
import ru.nsu.oop.model.Hand;
import ru.nsu.oop.view.GameView;

public abstract class Participant {

    private String name;
    private Hand hand = new Hand();

    Participant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public int getScore() {
        return hand.calculateScore();
    }

    public void receiveCard(Card card) {
        hand.addCard(card);
    }

    public abstract boolean makeDecision(GameView view);

}
