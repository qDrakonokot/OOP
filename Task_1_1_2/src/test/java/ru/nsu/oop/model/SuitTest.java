package ru.nsu.oop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SuitTest {

    @Test
    void testSuitLabels() {
        assertEquals("Пики", Suit.SPADES.getLabel());
        assertEquals("Червы", Suit.HEARTS.getLabel());
        assertEquals("Бубны", Suit.DIAMONDS.getLabel());
        assertEquals("Трефы", Suit.CLUBS.getLabel());
    }
}