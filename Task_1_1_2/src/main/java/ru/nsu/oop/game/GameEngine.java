package ru.nsu.oop.game;

import ru.nsu.oop.view.GameView;

/**
 * Класс реализующий основной движок игры.
 */
public class GameEngine {

    private final GameView view;

    /**
     * Конструктор с внедрением зависимости.
     *
     * @param view Интерфейс для взаимодействия с пользователем.
     */
    public GameEngine(GameView view) {
        this.view = view;
    }

    /**
     * Фабричный метод для создания раунда. Выделен специально для переопределения в тестах.
     *
     * @return Созданный раунд.
     */
    protected GameRound createRound() {
        return new GameRound(view);
    }

    /**
     * Полностью управляет игрой. На каждой итерации цикла создает новый объект раунда.
     */
    public void gameStart() {
        view.showMessage("Добро пожаловать в Блэкджек!");

        int currentRound = 1;
        int playerWins = 0;
        int dealerWins = 0;

        while (true) {
            view.showMessage("\nРаунд " + currentRound);

            GameRound round = createRound();
            RoundResult result = round.startRound();

            announceRoundResult(result);

            if (result == RoundResult.PLAYER_WINS) {
                playerWins += 1;
            } else if (result == RoundResult.DEALER_WINS) {
                dealerWins += 1;
            }

            view.showMessage("Счет " + playerWins + ":" + dealerWins + " в "
               + (playerWins >= dealerWins ? "вашу пользу" : "пользу дилера"));

            if (!view.askPlayAgain()) {
                view.showMessage("Игра окончена. Итоговый счет " + playerWins + ":" + dealerWins);
                break;
            }

            currentRound += 1;
        }
    }

    private void announceRoundResult(RoundResult result) {
        switch (result) {
            case DEALER_WINS -> view.showMessage("Победил дилер!");
            case PLAYER_WINS -> view.showMessage("Вы выиграли раунд!");
            case DRAW -> view.showMessage("Ничья!");
            default -> throw new IllegalArgumentException("Unknown value");
        }
    }
}
