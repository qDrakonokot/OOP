package ru.nsu.oop.actor;

import ru.nsu.oop.view.GameView;

/**
 * Класс Игрока, управляемого пользователем.
 */
public class Player extends Participant {

    public Player() {
        super("Вы");
    }

    /**
     * Запрашивает решение пользователя через переданный интерфейс отображения.
     *
     * @param view Интерфейс взаимодействия.
     * @return true, если игрок решил взять карту.
     */
    @Override
    public boolean makeDecision(GameView view) {
        return view.askPlayerMove();
    }
}