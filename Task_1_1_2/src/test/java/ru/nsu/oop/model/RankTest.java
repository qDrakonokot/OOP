package ru.nsu.oop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    void testRankValues() {
        // Проверка числовой карты
        assertEquals(7, Rank.SEVEN.getValue());

        // Проверка карты с картинкой
        assertEquals(10, Rank.QUEEN.getValue());

        // Проверка Туза
        assertEquals(11, Rank.ACE.getValue());
    }

    @Test
    void testRankLabels() {
        assertEquals("Туз", Rank.ACE.getLabel());
        assertEquals("Двойка", Rank.TWO.getLabel());
    }
}