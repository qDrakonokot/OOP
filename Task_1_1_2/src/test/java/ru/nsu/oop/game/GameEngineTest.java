package ru.nsu.oop.game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.oop.actor.Participant;
import ru.nsu.oop.model.Card;
import ru.nsu.oop.view.GameView;

/**
 * Тесты для глобального игрового цикла и системы подсчета очков.
 */
class GameEngineTest {

    /**
     * Метод-помощник для создания тестируемого экземпляра движка. Мы наследуемся от GameEngine "на
     * лету" и подменяем фабричный метод.
     */
    private GameEngine createTestableEngine(GameView view, RoundResult resultToReturn) {
        return new GameEngine(view) {
            @Override
            protected GameRound createRound() {
                return new StubRound(view, resultToReturn);
            }
        };
    }

    @Test
    void testPlayerWinsAndExits() {
        // Устанавливаем ответ пользователя "нет" после первого раунда
        StubView view = new StubView(false);
        // Заставляем раунд всегда возвращать победу игрока
        GameEngine engine = createTestableEngine(view, RoundResult.PLAYER_WINS);

        engine.gameStart();

        assertTrue(view.messages.contains("Вы выиграли раунд!"));
        assertTrue(view.messages.contains("Счет 1:0 в вашу пользу"));
        assertTrue(view.messages.contains("Игра окончена. Итоговый счет 1:0"));
    }

    @Test
    void testDealerWinsAndExits() {
        StubView view = new StubView(false);
        GameEngine engine = createTestableEngine(view, RoundResult.DEALER_WINS);

        engine.gameStart();

        assertTrue(view.messages.contains("Победил дилер!"));
        assertTrue(view.messages.contains("Счет 0:1 в пользу дилера"));
        assertTrue(view.messages.contains("Игра окончена. Итоговый счет 0:1"));
    }

    @Test
    void testDrawAndExits() {
        StubView view = new StubView(false);
        GameEngine engine = createTestableEngine(view, RoundResult.DRAW);

        engine.gameStart();

        assertTrue(view.messages.contains("Ничья!"));
        assertTrue(view.messages.contains("Счет 0:0 в вашу пользу"));
        assertTrue(view.messages.contains("Игра окончена. Итоговый счет 0:0"));
    }

    @Test
    void testMultipleRoundsScoreAccumulation() {
        // Игрок соглашается играть второй раунд, но отказывается от третьего
        StubView view = new StubView(true, false);

        // Для этого теста мы должны возвращать разные результаты.
        // Реализуем специальный движок прямо в тесте.
        GameEngine engine = new GameEngine(view) {
            private int roundCounter = 0;

            @Override
            protected GameRound createRound() {
                // В первом раунде выигрывает игрок, во втором — дилер
                RoundResult result = (roundCounter++ == 0)
                    ? RoundResult.PLAYER_WINS
                    : RoundResult.DEALER_WINS;
                return new StubRound(view, result);
            }
        };

        engine.gameStart();

        // Проверяем, что оба раунда были сыграны и счетчики отработали корректно
        assertTrue(view.messages.contains("\nРаунд 1"));
        assertTrue(view.messages.contains("\nРаунд 2"));
        assertTrue(view.messages.contains("Игра окончена. Итоговый счет 1:1"));
    }

    /**
     * Заглушка пользовательского интерфейса, которая перехватывает вывод в список и контролирует
     * ответы на вопросы "Сыграем еще?".
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
        public void showCards(Participant participant) {
            // Для тестов движка вывод карт не имеет значения
        }

        @Override
        public void showDealerHiddenCard(Card openCard) {
        }

        @Override
        public void showCardDrawn(Participant participant, Card card) {
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
            return false; // Предохранитель от бесконечного цикла
        }
    }

    /**
     * Заглушка раунда, возвращающая заранее запрограммированный результат.
     */
    private static class StubRound extends GameRound {

        private final RoundResult forcedResult;

        StubRound(GameView view, RoundResult forcedResult) {
            super(view);
            this.forcedResult = forcedResult;
        }

        @Override
        public RoundResult startRound() {
            return forcedResult;
        }
    }
}