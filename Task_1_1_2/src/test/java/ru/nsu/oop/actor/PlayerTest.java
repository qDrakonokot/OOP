package ru.nsu.oop.actor;

import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.Hand;
import ru.nsu.oop.view.GameView;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerName() {
        Player player = new Player();
        assertEquals("Вы", player.getName());
    }

    @Test
    void testMakeDecisionDelegatesToView() {
        Player player = new Player();

        // Создаем заглушку view, которая имитирует ввод "1" (взять карту)
        GameView alwaysTrueView = new GameView() {
            @Override
            public void showMessage(String message) {
            }

            @Override
            public void showCards(String owner, Hand hand) {
            }

            @Override
            public boolean askPlayerMove() {
                return true;
            }

            @Override
            public boolean askPlayAgain() {
                return false;
            }
        };

        // Создаем заглушку view, которая имитирует ввод "0" (остановиться)
        GameView alwaysFalseView = new GameView() {
            @Override
            public void showMessage(String message) {
            }

            @Override
            public void showCards(String owner, Hand hand) {
            }

            @Override
            public boolean askPlayerMove() {
                return false;
            }

            @Override
            public boolean askPlayAgain() {
                return false;
            }
        };

        // Метод должен вернуть ровно то, что ему ответил интерфейс
        assertTrue(player.makeDecision(alwaysTrueView));
        assertFalse(player.makeDecision(alwaysFalseView));
    }
}