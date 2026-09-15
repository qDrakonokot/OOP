package ru.nsu.oop.view;

import ru.nsu.oop.model.Hand;

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
     * Выводит текущие карты участника.
     *
     * @param ownerName Имя владельца карт (например, "Ваши карты").
     * @param hand Объект руки, содержащий карты.
     */
    void showCards(String ownerName, Hand hand);

    /**
     * Решение пользователя о взятии дополнительной карты.
     *
     * @return Решение пользователя о взятии дополнительной карты.
     */
    boolean askPlayerMove();

    /**
     * Решение пользователя о начале нового раунда.
     *
     * @return Решение пользователя о начале нового раунда.
     */
    boolean askPlayAgain();
}
