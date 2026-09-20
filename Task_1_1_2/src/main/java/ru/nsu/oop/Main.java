package ru.nsu.oop;

import ru.nsu.oop.game.GameEngine;
import ru.nsu.oop.view.ConsoleView;

/**
 * Точка входа в приложение Блэкджек.
 */
public class Main {

    /**
     * Запускает игру.
     */
    static void main(String[] args) {
        GameEngine engine = new GameEngine(new ConsoleView());
        engine.gameStart();
    }
}