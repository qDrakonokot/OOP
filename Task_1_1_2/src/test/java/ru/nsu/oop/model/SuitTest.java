package ru.nsu.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SuitTest {

    @Test
    void testSuitLabels() {
        assertEquals("Пики", Suit.SPADES.getLabel());
        assertEquals("Червы", Suit.HEARTS.getLabel());
        assertEquals("Бубны", Suit.DIAMONDS.getLabel());
        assertEquals("Трефы", Suit.CLUBS.getLabel());
    }
}