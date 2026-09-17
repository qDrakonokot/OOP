package ru.nsu.oop.game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.Hand;
import ru.nsu.oop.view.GameView;

class GameEngineTest {

    @Test
    void testPlayerWinsAndExits() {
        // Имитируем, что игрок не хочет играть второй раунд
        StubView view = new StubView(false);
        // Раунд гарантированно возвращает победу игрока
        StubRound round = new StubRound(view, RoundResult.PLAYER_WINS);

        GameEngine.gameStart(view, round);

        // Проверяем, что очки начислились и игра завершилась корректно
        assertTrue(view.messages.contains("Счет 1:0 в вашу пользу"));
        assertTrue(view.messages.contains("Игра окончена. Итоговый счет 1:0"));
    }

    @Test
    void testDealerWinsAndExits() {
        StubView view = new StubView(false);
        StubRound round = new StubRound(view, RoundResult.DEALER_WINS);

        GameEngine.gameStart(view, round);

        assertTrue(view.messages.contains("Счет 0:1 в пользу дилера"));
        assertTrue(view.messages.contains("Игра окончена. Итоговый счет 0:1"));
    }

    @Test
    void testDrawAndExits() {
        StubView view = new StubView(false);
        StubRound round = new StubRound(view, RoundResult.DRAW);

        GameEngine.gameStart(view, round);

        // При ничьей счет не меняется. Проверяем ветку логики "вашу пользу" при 0:0
        assertTrue(view.messages.contains("Счет 0:0 в вашу пользу"));
        assertTrue(view.messages.contains("Игра окончена. Итоговый счет 0:0"));
    }

    @Test
    void testMultipleRoundsScoreAccumulation() {
        // Игрок соглашается на второй раунд, но отказывается от третьего
        StubView view = new StubView(true, false);
        // В первом раунде выигрывает игрок, во втором — дилер
        StubRound round = new StubRound(view, RoundResult.PLAYER_WINS, RoundResult.DEALER_WINS);

        GameEngine.gameStart(view, round);

        // Убеждаемся, что цикл прошел два раза, и счетчики накопили результаты
        assertTrue(view.messages.contains("\nРаунд 1"));
        assertTrue(view.messages.contains("\nРаунд 2"));
        assertTrue(view.messages.contains("Игра окончена. Итоговый счет 1:1"));
    }

    /**
     * Заглушка для интерфейса GameView. Сохраняет все выведенные сообщения в список для последующей
     * проверки в assert.
     */
    private static class StubView implements GameView {

        final List<String> messages = new ArrayList<>();
        private final boolean[] playAgainAnswers;
        private int answerIndex = 0;

        StubView(boolean... playAgainAnswers) {
            this.playAgainAnswers = playAgainAnswers;
        }

        @Override
        public void showMessage(String message) {
            messages.add(message);
        }

        @Override
        public void showCards(String ownerName, Hand hand) {
            // В рамках тестирования движка вывод карт игнорируется
        }

        @Override
        public boolean askPlayerMove() {
            return false;
        }

        @Override
        public boolean askPlayAgain() {
            if (answerIndex < playAgainAnswers.length) {
                return playAgainAnswers[answerIndex++];
            }
            return false; // Защита от бесконечного цикла
        }
    }

    /**
     * Заглушка для GameRound. Переопределяет метод startRound, возвращая заранее заданные исходы
     * раундов.
     */
    private static class StubRound extends GameRound {

        private final RoundResult[] results;
        private int resultIndex = 0;

        StubRound(GameView view, RoundResult... results) {
            super(view); // Обязательный вызов конструктора родительского класса
            this.results = results;
        }

        @Override
        public RoundResult startRound() {
            if (resultIndex < results.length) {
                return results[resultIndex++];
            }
            return RoundResult.DRAW;
        }
    }
}