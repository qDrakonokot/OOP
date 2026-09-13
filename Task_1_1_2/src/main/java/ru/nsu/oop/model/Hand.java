package ru.nsu.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    public int calculateScore() {
        int score = 0;
        int aceCount = 0;

        for (Card el : cards) {
            score += el.getValue();
            if (el.getValue() == 11) {
                aceCount += 1;
            }
        }

        if (score > 21) {
            while (score > 21 && aceCount > 0) {
                score -= 10;
                aceCount -= 1;
            }
        }

        return score;
    }

    @Override
    public String toString() {
        return cards.toString() + " => " + calculateScore();
    }

}
