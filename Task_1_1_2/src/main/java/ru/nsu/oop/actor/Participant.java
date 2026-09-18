package ru.nsu.oop.actor;

import ru.nsu.oop.model.Card;
import ru.nsu.oop.model.Hand;
import ru.nsu.oop.model.ScoreCalculator;
import ru.nsu.oop.view.GameView;

/**
 * Базовый абстрактный класс участника игры в Блэкджек.
 */
public abstract class Participant {

    private final String name;
    private final Hand hand = new Hand();

    /**
     * Имя участника.
     *
     * @param name Имя участника.
     */
    Participant(String name) {
        this.name = name;
    }

    /**
     * Имя участника.
     *
     * @return Имя участника.
     */
    public String getName() {
        return name;
    }

    /**
     * Объект руки, содержащий текущие карты.
     *
     * @return Объект руки, содержащий текущие карты.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Текущий счет участника.
     *
     * @return Текущий счет участника.
     */
    public int getScore() {
        return ScoreCalculator.calculateScore(hand);
    }

    /**
     * Добавляет полученную карту в руку участника.
     *
     * @param card Полученная карта.
     */
    public void receiveCard(Card card) {
        hand.addCard(card);
    }

    /**
     * Абстрактный метод принятия решения о взятии следующей карты.
     *
     * @param view Интерфейс для возможного взаимодействия с пользователем.
     * @return true, если участник берет карту, и false, если останавливается.
     */
    public abstract boolean makeDecision(GameView view);

}
