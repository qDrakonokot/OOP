package ru.nsu.oop.game;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.Hand;
import ru.nsu.oop.view.GameView;

class GameRoundTest {

    @Test
    void testStartRoundFullCoverage() {
        // Играем 2000 раз, чтобы гарантированно поймать блэкджеки, переборы и ничьи
        for (int i = 0; i < 2000; i++) {
            GameView dummyView = new GameView() {
                private int moveCount = 0;

                @Override
                public void showMessage(String message) {
                }

                @Override
                public void showCards(String ownerName, Hand hand) {
                }

                @Override
                public boolean askPlayerMove() {
                    // Игрок берет ровно одну карту и всегда останавливается
                    return moveCount++ == 0;
                }

                @Override
                public boolean askPlayAgain() {
                    return false;
                }
            };

            GameRound engine = new GameRound(dummyView);
            assertNotNull(engine.startRound());
        }
    }
}