package ru.nsu.oop.game;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import ru.nsu.oop.actor.Participant;
import ru.nsu.oop.model.Card;
import ru.nsu.oop.view.GameView;

class GameRoundTest {

    @Test
    void testPlayReturnsValidResultWithoutExceptions() {
        GameView view = new PassiveStubView();
        GameRound round = new GameRound(view);

        RoundResult result = round.startRound();

        // Проверяем, что раунд отработал от начала и до конца
        assertNotNull(result);
    }

    private static class PassiveStubView implements GameView {

        @Override
        public void showMessage(String message) {
        }

        @Override
        public void showCards(Participant participant) {
        }

        @Override
        public void showDealerHiddenCard(Card openCard) {
        }

        @Override
        public void showCardDrawn(Participant participant, Card card) {
        }

        @Override
        public boolean askPlayerMove() {
            // Всегда отказываемся от добора, чтобы раунд завершился мгновенно
            return false;
        }

        @Override
        public boolean askPlayAgain() {
            return false;
        }
    }
}