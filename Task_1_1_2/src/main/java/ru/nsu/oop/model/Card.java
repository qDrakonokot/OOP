package ru.nsu.oop.model;

/**
 * Класс, представляющий неизменяемую игральную карту.
 */
public class Card {

    private final Rank rank;
    private final Suit suit;

    /**
     * Создает новую карту с заданными достоинством и мастью.
     *
     * @param rank Достоинство карты.
     * @param suit Масть карты.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Количество очков карты.
     *
     * @return Количество очков карты.
     */
    public int getValue() {
        return rank.getValue();
    }

    /**
     * Форматирует карту для отображения (например, "Двойка Пики (2)").
     *
     * @return Строковое представление карты.
     */
    @Override
    public String toString() {
        return rank.getLabel() + " " + suit.getLabel() + " (" + rank.getValue() + ")";
    }
}
