package ru.nsu.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HandTest {

    @Test
    void testScoreWithoutAces() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.TEN, Suit.SPADES));
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));

        assertEquals(17, hand.calculateScore()); // 10 + 7
    }

    @Test
    void testScoreWithAceNoBust() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));

        assertEquals(18, hand.calculateScore()); // Туз считается как 11
    }

    @Test
    void testScoreWithAceBust() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.SPADES)); // 11
        hand.addCard(new Card(Rank.TEN, Suit.HEARTS)); // 10
        hand.addCard(new Card(Rank.SIX, Suit.CLUBS));  // 6

        // 11 + 10 + 6 = 27 (перебор). Туз превращается в 1. Итог: 1 + 10 + 6 = 17
        assertEquals(17, hand.calculateScore());
    }

    @Test
    void testScoreWithMultipleAces() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));

        // Два туза - это 11 + 1 = 12
        assertEquals(12, hand.calculateScore());
    }

    @Test
    void testToStringFormat() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.TEN, Suit.SPADES));
        hand.addCard(new Card(Rank.THREE, Suit.HEARTS));

        // Проверяем наличие корректно отформатированной стрелочки и результата
        assertEquals("[Десятка Пики (10), Тройка Червы (3)] => 13", hand.toString());
    }
}