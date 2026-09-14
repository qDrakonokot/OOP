package ru.nsu.oop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий карты на руках у участника игры.
 * Отвечает за хранение текущих карт и математический расчет очков.
 */
public class Hand {

    private final List<Card> cards = new ArrayList<>();

    /**
     * Добавляет вытянутую карту в руку.
     *
     * @param card Новая карта.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Возвращает неизменяемую копию списка карт.
     *
     * @return Список карт на руках.
     */
    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    /**
     * Вычисляет итоговую сумму очков, динамически обрабатывая Тузы
     * (превращает 11 очков в 1 при переборе за 21).
     *
     * @return Сумма очков комбинации.
     */
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

    /**
     * Форматирует карты на руках для отображения.
     *
     * @return Строковое представление карт на руках + их вес.
     */
    @Override
    public String toString() {
        return cards.toString() + " => " + calculateScore();
    }

}
