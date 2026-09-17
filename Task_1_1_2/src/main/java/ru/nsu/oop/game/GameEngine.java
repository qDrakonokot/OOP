package ru.nsu.oop.game;

import ru.nsu.oop.view.GameView;

/**
 * Класс реализующий основной движок игры.
 */
public class GameEngine {

    /**
     * Запускает бесконечный цикл игры. Управляет очками и текущим раундом.
     *
     * @param view      Объект пользовательского интерфейса.
     * @param gameRound Объект одного раунда.
     */
    public static void gameStart(GameView view, GameRound gameRound) {
        view.showMessage("Добро пожаловать в Блэкджек!");

        int round = 1;
        int playerWins = 0;
        int dealerWins = 0;

        while (true) {
            view.showMessage("\nРаунд " + round);

            RoundResult result = gameRound.startRound();

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
