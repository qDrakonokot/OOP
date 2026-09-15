package ru.nsu.oop;

import ru.nsu.oop.game.GameEngine;
import ru.nsu.oop.game.RoundResult;
import ru.nsu.oop.view.ConsoleView;
import ru.nsu.oop.view.GameView;

/**
 * Точка входа в приложение Блэкджек. Управляет глобальным циклом раундов и сохраняет общий счет.
 */
public class Main {

    /**
     * Запускает приложение. Инициализирует консольный интерфейс, движок и запускает бесконечный
     * цикл.
     */
    static void main(String[] args) {
        GameView view = new ConsoleView();
        GameEngine engine = new GameEngine(view);
        view.showMessage("Добро пожаловать в Блэкджек!");

        int round = 1;
        int playerWins = 0;
        int dealerWins = 0;

        while (true) {
            view.showMessage("\nРаунд " + round);

            RoundResult result = engine.startRound();

            if (result == RoundResult.PLAYER_WINS) {
                playerWins += 1;
            } else if (result == RoundResult.DEALER_WINS) {
                dealerWins += 1;
            }

            view.showMessage("Счет " + playerWins + ":" + dealerWins + " в " +
                (playerWins >= dealerWins ? "вашу пользу" : "пользу дилера"));

            if (!view.askPlayAgain()) {
                view.showMessage("Игра окончена. Итоговый счет " + playerWins + ":" + dealerWins);
                break;
            }
            round += 1;
        }
    }
}