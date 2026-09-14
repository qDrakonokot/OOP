package ru.nsu.oop.view;

import ru.nsu.oop.model.Hand;

/**
 * Контракт для реализации пользовательского интерфейса игры. Отделяет бизнес-логику от конкретного
 * способа ввода/вывода.
 */
public interface GameView {

    /**
     * Выводит текстовое сообщение.
     */
    void showMessage(String message);

    /**
     * Выводит текущие карты участника.
     */
    void showCards(String ownerName, Hand hand);

    /**
     * @return Решение пользователя о взятии дополнительной карты.
     */
    boolean askPlayerMove();

    /**
     * @return Решение пользователя о начале нового раунда.
     */
    boolean askPlayAgain();
}
