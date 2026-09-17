package ru.nsu.oop.game;

import ru.nsu.oop.actor.Dealer;
import ru.nsu.oop.actor.Participant;
import ru.nsu.oop.actor.Player;
import ru.nsu.oop.model.Card;
import ru.nsu.oop.model.Deck;
import ru.nsu.oop.model.GameConstants;
import ru.nsu.oop.view.GameView;

/**
 * Один раунд игры. Управляет жизненным циклом одного раунда, очередностью ходов и определяет
 * победителя.
 */
public class GameRound {

    private final GameView view;

    /**
     * Создает объект раунда с привязкой к конкретному интерфейсу.
     *
     * @param view Объект пользовательского интерфейса.
     */
    public GameRound(GameView view) {
        this.view = view;
    }

    /**
     * Запускает полный цикл одного раунда: раздачу, ходы участников и подсчет итогов.
     *
     * @return Результат завершенного раунда.
     */
    public RoundResult startRound() {
        final int PLAYERS_CARDS_COUNT_TO_PICK_UP = 2;
        Deck deck = new Deck();
        Participant player = new Player();
        Participant dealer = new Dealer();

        for (int i = 0; i < PLAYERS_CARDS_COUNT_TO_PICK_UP; i++) {
            player.receiveCard(deck.draw());
            dealer.receiveCard(deck.draw());
        }

        view.showCards("Ваши карты", player.getHand());
        view.showMessage(
            "Карты дилера: [" + dealer.getHand().getCards().get(0) + ", <закрытая карта>]");

        if (player.getScore() == GameConstants.BLACK_JACK) {
            view.showMessage("Блэкджек! Вы выиграли раунд!");
            return RoundResult.PLAYER_WINS;
        }
        if (dealer.getScore() == GameConstants.BLACK_JACK) {
            view.showMessage("У Дилера блэкджек! Вы проиграли.");
            return RoundResult.DEALER_WINS;
        }

        while (player.getScore() < GameConstants.BLACK_JACK && player.makeDecision(view)) {
            Card drawn = deck.draw();
            player.receiveCard(drawn);
            view.showMessage("Вы открыли карту: " + drawn);
            view.showCards("Ваши карты", player.getHand());

            if (player.getScore() > GameConstants.BLACK_JACK) {
                view.showMessage("Перебор! Вы проиграли раунд.");
                return RoundResult.DEALER_WINS;
            }
        }

        view.showCards("Ход дилера. Карты дилера", dealer.getHand());
        while (dealer.makeDecision(view)) {
            Card drawn = deck.draw();
            dealer.receiveCard(drawn);
            view.showMessage("Дилер открывает карту: " + drawn);
            view.showCards("Карты дилера", dealer.getHand());

            if (dealer.getScore() > GameConstants.BLACK_JACK) {
                view.showMessage("У Дилера перебор! Вы выиграли раунд.");
                return RoundResult.PLAYER_WINS;
            }
        }

        if (dealer.getScore() > player.getScore()) {
            view.showMessage("Победил дилер!");
            return RoundResult.DEALER_WINS;
        } else if (player.getScore() > dealer.getScore()) {
            view.showMessage("Вы выиграли раунд!");
            return RoundResult.PLAYER_WINS;
        } else {
            view.showMessage("Ничья!");
            return RoundResult.DRAW;
        }
    }

}
