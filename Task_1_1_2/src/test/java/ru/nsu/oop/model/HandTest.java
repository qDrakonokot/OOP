package ru.nsu.oop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void testAddCardAndGetCards() {
        Hand hand = new Hand();
        Card card1 = new Card(Rank.TEN, Suit.SPADES);
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);

        hand.addCard(card1);
        hand.addCard(card2);

        List<Card> cards = hand.getCards();

        assertEquals(2, cards.size());
        assertEquals(card1, cards.get(0));
        assertEquals(card2, cards.get(1));
    }

    @Test
    void testGetCardsReturnsUnmodifiableList() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.TWO, Suit.CLUBS));

        List<Card> cards = hand.getCards();

        // Проверяем инкапсуляцию: попытка изменить полученный список должна вызвать ошибку
        assertThrows(UnsupportedOperationException.class,
            () -> cards.add(new Card(Rank.FIVE, Suit.DIAMONDS)));
    }

    @Test
    void testToStringFormat() {
        Hand hand = new Hand();
        Card card = new Card(Rank.TEN, Suit.SPADES);
        hand.addCard(card);

        String result = hand.toString();

        // Проверяем, что toString корректно склеивает список и результат калькулятора
        assertTrue(result.contains(card.toString()));
        assertTrue(result.contains("=>"));
    }
}