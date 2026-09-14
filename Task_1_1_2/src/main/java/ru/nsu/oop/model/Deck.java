package ru.nsu.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, представляющий игровую колоду из 52 карт.
 */
public class Deck {

    private List<Card> cards = new ArrayList<>();

    /**
     * Инициализирует стандартную колоду и автоматически перемешивает карты.
     */
    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * Вытягивает верхнюю карту из колоды.
     *
     * @return Удаленная из колоды карта.
     */
    public Card draw() {
        return cards.removeFirst();
    }

}
