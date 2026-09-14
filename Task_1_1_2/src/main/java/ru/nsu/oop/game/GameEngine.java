package ru.nsu.oop.game;

import ru.nsu.oop.actor.Dealer;
import ru.nsu.oop.actor.Player;
import ru.nsu.oop.model.Card;
import ru.nsu.oop.model.Deck;
import ru.nsu.oop.view.GameView;

/**
 * Движок игры. Управляет жизненным циклом одного раунда,
 * очередностью ходов и определяет победителя.
 */
public class GameEngine {

    private GameView view;
    private Deck deck;
    private Player player;
    private Dealer dealer;

    /**
     * Создает движок с привязкой к конкретному интерфейсу.
     *
     * @param view Объект пользовательского интерфейса.
     */
    public GameEngine(GameView view) {
        this.view = view;
    }

    /**
     * Запускает полный цикл одного раунда: раздачу, ходы участников и подсчет итогов.
     *
     * @return Результат завершенного раунда.
     */
    public RoundResult startRound() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();

        for (int i = 0; i < 2; i++) {
            player.receiveCard(deck.draw());
            dealer.receiveCard(deck.draw());
        }

        view.showCards("Ваши карты", player.getHand());
        view.showMessage(
            "Карты дилера: [" + dealer.getHand().getCards().get(0) + ", <закрытая карта>]");

        if (player.getScore() == 21) {
            view.showMessage("Блэкджек! Вы выиграли раунд!");
            return RoundResult.PLAYER_WINS;
        }
        if (dealer.getScore() == 21) {
            view.showMessage("У Дилера блэкджек! Вы проиграли.");
            return RoundResult.DEALER_WINS;
        }

        while (player.getScore() < 21 && player.makeDecision(view)) {
            Card drawn = deck.draw();
            player.receiveCard(drawn);
            view.showMessage("Вы открыли карту: " + drawn);
            view.showCards("Ваши карты", player.getHand());

            if (player.getScore() > 21) {
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

            if (dealer.getScore() > 21) {
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



















