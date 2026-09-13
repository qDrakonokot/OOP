package ru.nsu.oop.model;

public enum Suit {
    SPADES("Пики"),
    HEARTS("Червы"),
    DIAMONDS("Бубны"),
    CLUBS("Трефы");

    private final String label;

    Suit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
