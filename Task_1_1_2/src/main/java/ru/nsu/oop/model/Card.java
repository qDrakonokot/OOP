package ru.nsu.oop.model;

public class Card {

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return rank.getLabel() + " " + suit.getLabel() + " (" + rank.getValue() + ")";
    }
}
