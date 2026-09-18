package ru.nsu.oop.model;

/**
 * Класс вычисления очков в игре.
 */
public class ScoreCalculator {

    /**
     * Вычисляет итоговую сумму очков, динамически обрабатывая Тузы (превращает 11 очков в 1 при
     * переборе за 21).
     *
     * @return Сумма очков комбинации.
     */
    public static int calculateScore(Hand hand) {
        int score = 0;
        int aceCount = 0;

        for (Card card : hand.getCards()) {
            score += card.getValue();
            if (card.getValue() == GameConstants.ACE_VALUE) {
                aceCount += 1;
            }
        }

        if (score > GameConstants.BLACK_JACK) {
            while (score > GameConstants.BLACK_JACK && aceCount > 0) {
                score -= GameConstants.ACE_VALUE_DECREMENT_ON_OVERSCORE;
                aceCount -= 1;
            }
        }

        return score;
    }
}
