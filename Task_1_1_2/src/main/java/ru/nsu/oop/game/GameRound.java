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

    private static final int CARDS_TO_DEAL = 2;

    private final GameView view;
    private Deck deck;
    private Participant player;
    private Participant dealer;

    /**
     * Создает объект раунда с привязкой к конкретному интерфейсу.
     *
     * @param view Объект пользовательского интерфейса.
     */
    public GameRound(GameView view) {
        this.view = view;
    }

    /**
     * Запускает полный цикл одного раунда. Управляет раздачей, ходами участников и подсчетом
     * итогов.
     *
     * @return Результат завершенного раунда.
     */
    public RoundResult startRound() {

        initParticipantsAndDeck();
        dealInitialCards();
        showInitialCards();

        RoundResult blackjackResult = checkInitialBlackjack();
        if (blackjackResult != null) {
            return blackjackResult;
        }

        if (isPlayerBusted()) {
            return RoundResult.DEALER_WINS;
        }

        if (isDealerBusted()) {
            return RoundResult.PLAYER_WINS;
        }

        return determineWinner();
    }

    private void initParticipantsAndDeck() {
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();
    }

    private void dealInitialCards() {
        for (int i = 0; i < CARDS_TO_DEAL; i++) {
            player.receiveCard(deck.draw());
            dealer.receiveCard(deck.draw());
        }
    }

    private void showInitialCards() {
        view.showCards("Ваши карты", player.getHand());
        view.showMessage(
            "Карты дилера: [" + dealer.getHand().getCards().get(0) + ", <закрытая карта>]");
    }

    private RoundResult checkInitialBlackjack() {
        if (player.getScore() == GameConstants.BLACK_JACK) {
            view.showMessage("Блэкджек! Вы выиграли раунд!");
            return RoundResult.PLAYER_WINS;
        }
        if (dealer.getScore() == GameConstants.BLACK_JACK) {
            view.showMessage("У Дилера блэкджек! Вы проиграли.");
            return RoundResult.DEALER_WINS;
        }
        return null;
    }

    private boolean isPlayerBusted() {
        while (player.getScore() < GameConstants.BLACK_JACK && player.makeDecision(view)) {
            Card drawn = deck.draw();
            player.receiveCard(drawn);
            view.showMessage("Вы открыли карту: " + drawn);
            view.showCards("Ваши карты", player.getHand());

            if (player.getScore() > GameConstants.BLACK_JACK) {
                view.showMessage("Перебор! Вы проиграли раунд.");
                return true;
            }
        }
        return false;
    }

    private boolean isDealerBusted() {
        view.showCards("Ход дилера. Карты дилера", dealer.getHand());
        while (dealer.makeDecision(view)) {
            Card drawn = deck.draw();
            dealer.receiveCard(drawn);
            view.showMessage("Дилер открывает карту: " + drawn);
            view.showCards("Карты дилера", dealer.getHand());

            if (dealer.getScore() > GameConstants.BLACK_JACK) {
                view.showMessage("У Дилера перебор! Вы выиграли раунд.");
                return true;
            }
        }
        return false;
    }

    private RoundResult determineWinner() {
        if (dealer.getScore() > player.getScore()) {
            view.showMessage("Победил дилер!");
            return RoundResult.DEALER_WINS;
        }
        if (player.getScore() > dealer.getScore()) {
            view.showMessage("Вы выиграли раунд!");
            return RoundResult.PLAYER_WINS;
        }
        view.showMessage("Ничья!");
        return RoundResult.DRAW;
    }

}
