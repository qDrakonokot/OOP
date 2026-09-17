package ru.nsu.oop.actor;

import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.Card;
import ru.nsu.oop.model.Rank;
import ru.nsu.oop.model.Suit;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    @Test
    void testDealerName() {
        Dealer dealer = new Dealer();
        assertEquals("Дилер", dealer.getName());
    }

    @Test
    void testMakeDecisionLogic() {
        Dealer dealer = new Dealer();

        // Счет 0 (< 17), дилер должен брать карту
        assertTrue(dealer.makeDecision(null));

        // Доводим счет до 16
        dealer.receiveCard(new Card(Rank.TEN, Suit.SPADES));
        dealer.receiveCard(new Card(Rank.SIX, Suit.HEARTS));

        // Счет 16 (< 17), дилер все еще берет карту
        assertTrue(dealer.makeDecision(null));

        // Доводим счет до 18
        dealer.receiveCard(new Card(Rank.TWO, Suit.CLUBS));

        // Счет 18 (>= 17), дилер обязан остановиться
        assertFalse(dealer.makeDecision(null));
    }
}