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
    private final Deck deck;
    private final Participant player;
    private final Participant dealer;

    /**
     * Создает объект раунда с привязкой к конкретному интерфейсу.
     *
     * @param view Объект пользовательского интерфейса.
     */
    public GameRound(GameView view) {
        this.view = view;
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();
    }

    /**
     * Запускает полный цикл одного раунда. Управляет раздачей, ходами участников и подсчетом
     * итогов.
     *
     * @return Результат завершенного раунда.
     */
    public RoundResult startRound() {
        dealInitialCards();

        // Если с раздачи блэкджек у кого-то из участников, доборы пропускаются
        if (!hasInitialBlackjack()) {
            playTurn(player);

            // Дилер ходит только если игрок не допустил перебор
            if (!isBusted(player)) {
                playTurn(dealer);
            }
        }

        return determineWinner();
    }


    private void dealInitialCards() {
        for (int i = 0; i < CARDS_TO_DEAL; i++) {
            player.receiveCard(deck.draw());
            dealer.receiveCard(deck.draw());
        }

        view.showCards(player);
        view.showDealerHiddenCard(dealer.getHand().getCards().get(0));
    }

    private boolean hasInitialBlackjack() {
        return player.getScore() == GameConstants.BLACK_JACK
            || dealer.getScore() == GameConstants.BLACK_JACK;
    }

    private void playTurn(Participant participant) {
        while (participant.getScore() < GameConstants.BLACK_JACK
            && participant.makeDecision(view)) {
            Card card = deck.draw();
            participant.receiveCard(card);
            view.showCardDrawn(participant, card);
            view.showCards(participant);
        }
    }

    private boolean isBusted(Participant participant) {
        return participant.getScore() > GameConstants.BLACK_JACK;
    }

    private RoundResult determineWinner() {
        if (isBusted(player)) {
            return RoundResult.DEALER_WINS;
        }
        if (isBusted(dealer)) {
            return RoundResult.PLAYER_WINS;
        }

        int playerScore = player.getScore();
        int dealerScore = dealer.getScore();

        if (playerScore > dealerScore) {
            return RoundResult.PLAYER_WINS;
        }
        if (dealerScore > playerScore) {
            return RoundResult.DEALER_WINS;
        }
        return RoundResult.DRAW;
    }

}
