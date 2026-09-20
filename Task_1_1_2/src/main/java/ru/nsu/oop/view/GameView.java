package ru.nsu.oop.view;

import ru.nsu.oop.actor.Participant;
import ru.nsu.oop.model.Card;

/**
 * Контракт для реализации пользовательского интерфейса игры. Отделяет бизнес-логику от конкретного
 * способа ввода/вывода.
 */
public interface GameView {

    /**
     * Выводит текстовое сообщение.
     *
     * @param message Текст сообщения для вывода.
     */
    void showMessage(String message);

    /**
     * Выводит карты участника.
     *
     * @param participant участник.
     */
    void showCards(Participant participant);

    /**
     * Выводит карты дилера в ходе игры, как описано в условии задачи.
     *
     * @param openCard карта для показа.
     */
    void showDealerHiddenCard(Card openCard);

    /**
     * Показывает открытую карту участника.
     *
     * @param participant участник.
     * @param card        только что открытая карта для показа.
     */
    void showCardDrawn(Participant participant, Card card);

    /**
     * Спросить пользователя о решении брать карту. (исправлен коммент для метода).
     *
     * @return Решение пользователя о взятии дополнительной карты.
     */
    boolean askPlayerMove();

    /**
     * Спросить пользователя о решении начать новый раунд.
     *
     * @return Решение пользователя о начале нового раунда.
     */
    boolean askPlayAgain();
}
