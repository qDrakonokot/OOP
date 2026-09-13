package ru.nsu.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.removeFirst();
    }

}
