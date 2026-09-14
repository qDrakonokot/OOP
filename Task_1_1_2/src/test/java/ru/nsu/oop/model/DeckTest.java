package ru.nsu.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckHasExactly52Cards() {
        Deck deck = new Deck();

        // Мы должны успешно вытянуть 52 карты
        for (int i = 0; i < 52; i++) {
            assertNotNull(deck.draw());
        }

        // На 53-й карте коллекция должна выбросить исключение (NoSuchElementException или IndexOutOfBoundsException)
        assertThrows(RuntimeException.class, deck::draw);
    }
}