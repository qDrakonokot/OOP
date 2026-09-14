package ru.nsu.oop.model;

/**
 * Перечисление достоинств игральных карт. Хранит базовую стоимость карты в очках и ее русское
 * название.
 */
public enum Rank {
    TWO(2, "Двойка"),
    THREE(3, "Тройка"),
    FOUR(4, "Четверка"),
    FIVE(5, "Пятерка"),
    SIX(6, "Шестерка"),
    SEVEN(7, "Семерка"),
    EIGHT(8, "Восьмерка"),
    NINE(9, "Девятка"),
    TEN(10, "Десятка"),
    JACK(10, "Валет"),
    QUEEN(10, "Дама"),
    KING(10, "Король"),
    ACE(11, "Туз");

    private final int value;
    private final String label;

    Rank(int value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * @return Базовое количество очков, которое дает карта.
     */
    public int getValue() {
        return value;
    }

    /**
     * @return Русское название достоинства карты.
     */
    public String getLabel() {
        return label;
    }
}
