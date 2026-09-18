package ru.nsu.oop.model;

/**
 * Глобальные константы для математических расчетов и проверки условий игры.
 */
public class GameConstants {

    public static final int ACE_VALUE = Rank.ACE.getValue();
    // На это число мы уменьшаем общий счет, если он превысил блэкджек и на руке есть тузы.
    public static final int ACE_VALUE_DECREMENT_ON_OVERSCORE = ACE_VALUE - 1;
    public static final int BLACK_JACK = 21;

}
