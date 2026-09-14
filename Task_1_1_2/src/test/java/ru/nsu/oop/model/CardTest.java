package ru.nsu.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    @Test
    void testGetValue() {
        Card card = new Card(Rank.SEVEN, Suit.SPADES);
        assertEquals(7, card.getValue()); // Проверка числовой карты

        Card ace = new Card(Rank.ACE, Suit.HEARTS);
        assertEquals(11, ace.getValue()); // Проверка туза
    }

    @Test
    void testToStringFormat() {
        Card card = new Card(Rank.QUEEN, Suit.HEARTS);
        assertEquals("Дама Червы (10)", card.toString()); // Строго по формату вывода
    }
}