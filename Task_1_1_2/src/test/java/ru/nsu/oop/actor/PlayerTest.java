package ru.nsu.oop.actor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.Card;
import ru.nsu.oop.view.GameView;

class PlayerTest {

    @Test
    void testMakeDecisionReturnsTrue() {
        Player player = new Player();
        GameView view = new StubView(true);

        assertTrue(player.makeDecision(view));
    }

    @Test
    void testMakeDecisionReturnsFalse() {
        Player player = new Player();
        GameView view = new StubView(false);

        assertFalse(player.makeDecision(view));
    }

    private record StubView(boolean responseToMove) implements GameView {

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
            return responseToMove;
        }

        @Override
        public boolean askPlayAgain() {
            return false;
        }
    }
}