package ru.nsu.oop.model;

/**
 * Класс вычисления очков в игре.
 */
public class Calculate {

    /**
     * Вычисляет итоговую сумму очков, динамически обрабатывая Тузы (превращает 11 очков в 1 при
     * переборе за 21).
     *
     * @return Сумма очков комбинации.
     */
    public static int calculateScore(Hand hand) {
        int score = 0;
        int aceCount = 0;

        for (Card el : hand.getCards()) {
            score += el.getValue();
            if (el.getValue() == GameConstants.ACE_VALUE) {
                aceCount += 1;
            }
        }

        if (score > GameConstants.BLACK_JACK) {
            while (score > GameConstants.BLACK_JACK && aceCount > 0) {
                score -= GameConstants.ACE_VALUE_FOR_REBALANCE_OVERSCORE;
                aceCount -= 1;
            }
        }

        return score;
    }
}
