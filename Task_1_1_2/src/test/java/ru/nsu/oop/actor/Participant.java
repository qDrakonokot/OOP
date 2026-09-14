package ru.nsu.oop.actor;

import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.Card;
import ru.nsu.oop.model.Rank;
import ru.nsu.oop.model.Suit;
import ru.nsu.oop.view.GameView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParticipantTest {

    @Test
    void testParticipantBaseMethods() {
        // Создаем анонимный класс-наследник для тестирования базовой логики
        Participant participant = new Participant("Тест") {
            @Override
            public boolean makeDecision(GameView view) {
                return false; // Заглушка, так как метод абстрактный
            }
        };

        // Проверяем, что имя сохранилось корректно
        assertEquals("Тест", participant.getName());

        // Проверяем изначальный счет (должен быть 0)
        assertEquals(0, participant.getScore());

        // Добавляем карту и проверяем, что счет обновился
        participant.receiveCard(new Card(Rank.TEN, Suit.SPADES));
        assertEquals(10, participant.getScore());
    }
}