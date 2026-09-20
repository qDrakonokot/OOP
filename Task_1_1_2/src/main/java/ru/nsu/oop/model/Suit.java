package ru.nsu.oop.model;

/**
 * Перечисление мастей игральных карт.
 */
public enum Suit {
    SPADES("Пики"),
    HEARTS("Червы"),
    DIAMONDS("Бубны"),
    CLUBS("Трефы");

    private final String label;

    Suit(String label) {
        this.label = label;
    }

    /**
     * Русское название масти для вывода в консоль.
     *
     * @return Русское название масти для вывода в консоль.
     */
    public String getLabel() {
        return label;
    }
}
