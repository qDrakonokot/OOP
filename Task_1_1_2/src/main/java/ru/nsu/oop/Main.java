package ru.nsu.oop;

import ru.nsu.oop.game.GameEngine;
import ru.nsu.oop.game.GameRound;
import ru.nsu.oop.view.ConsoleView;
import ru.nsu.oop.view.GameView;

/**
 * Точка входа в приложение Блэкджек.
 */
public class Main {

    /**
     * Инициализирует консольный интерфейс, движок и запускает игру.
     */
    static void main(String[] args) {
        GameView view = new ConsoleView();
        GameRound gameRound = new GameRound(view);

        GameEngine.gameStart(view, gameRound);
    }
}