package ru.nsu.oop.game;

import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.Hand;
import ru.nsu.oop.view.GameView;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    @Test
    void testStartRoundCompletesSuccessfully() {
        // Заглушка, которая имитирует пассивного игрока (сразу останавливается)
        GameView dummyView = new GameView() {
            @Override public void showMessage(String message) {}
            @Override public void showCards(String ownerName, Hand hand) {}
            @Override public boolean askPlayerMove() {
                return false; // Игрок не берет дополнительные карты
            }
            @Override public boolean askPlayAgain() { return false; }
        };

        GameEngine engine = new GameEngine(dummyView);
        RoundResult result = engine.startRound();

        // Так как колода перемешивается случайно, мы не знаем точного победителя,
        // но гарантируем, что результат не null и принадлежит нашему enum
        assertNotNull(result);
        assertTrue(result == RoundResult.PLAYER_WINS ||
                result == RoundResult.DEALER_WINS ||
                result == RoundResult.DRAW);
    }
}