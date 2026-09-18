package ru.nsu.oop.view;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.nsu.oop.actor.Participant;
import ru.nsu.oop.actor.Player;
import ru.nsu.oop.model.Card;
import ru.nsu.oop.model.Rank;
import ru.nsu.oop.model.Suit;

/**
 * Тесты для консольного интерфейса. Проверяют корректность вывода в консоль и считывания данных от
 * пользователя.
 */
class ConsoleViewTest {

    private final InputStream standardIn = System.in;
    private final PrintStream standardOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    /**
     * Перехватываем стандартный вывод перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Восстанавливаем оригинальные потоки после каждого теста, чтобы не сломать логирование в
     * других классах.
     */
    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    /**
     * Вспомогательный метод для подмены пользовательского ввода. Должен вызываться СТРОГО до
     * создания объекта ConsoleView.
     */
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testShowMessage() {
        // Подготавливаем пустой ввод, чтобы сканер не выбросил ошибку при инициализации
        provideInput("");
        ConsoleView view = new ConsoleView();

        view.showMessage("Тестовое сообщение"); //

        assertTrue(outputStreamCaptor.toString().contains("Тестовое сообщение"));
    }

    @Test
    void testAskPlayerMoveReturnsTrueOnOne() {
        // Имитируем ввод единицы и нажатие Enter
        provideInput("1\n");
        ConsoleView view = new ConsoleView();

        boolean result = view.askPlayerMove(); //[cite: 17]

        assertTrue(result);
        assertTrue(outputStreamCaptor.toString().contains("Введите \"1\"")); //[cite: 17]
    }

    @Test
    void testAskPlayerMoveReturnsFalseOnZero() {
        provideInput("0\n");
        ConsoleView view = new ConsoleView();

        boolean result = view.askPlayerMove(); //[cite: 17]

        assertFalse(result);
    }

    @Test
    void testAskPlayAgainReturnsTrueOnOne() {
        provideInput("1\n");
        ConsoleView view = new ConsoleView();

        boolean result = view.askPlayAgain(); //[cite: 17]

        assertTrue(result);
        assertTrue(outputStreamCaptor.toString().contains("Хотите сыграть еще?")); //[cite: 17]
    }

    @Test
    void testShowCards() {
        provideInput("");
        ConsoleView view = new ConsoleView();
        Participant player = new Player();
        Card card = new Card(Rank.TEN, Suit.SPADES);
        player.receiveCard(card);

        view.showCards(player);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Карты ("));
        // Проверяем наличие карты, используя её собственный метод toString()
        assertTrue(output.contains(card.toString()));
    }

    @Test
    void testShowDealerHiddenCard() {
        provideInput("");
        ConsoleView view = new ConsoleView();
        Card card = new Card(Rank.ACE, Suit.HEARTS);

        view.showDealerHiddenCard(card);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Карты дилера: ["));
        assertTrue(output.contains(card.toString()));
        assertTrue(output.contains("<закрытая карта>"));
    }

    @Test
    void testShowCardDrawn() {
        provideInput("");
        ConsoleView view = new ConsoleView();
        Participant player = new Player();
        Card card = new Card(Rank.KING, Suit.CLUBS);

        view.showCardDrawn(player, card);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("открывает карту: "));
        assertTrue(output.contains(card.toString()));
    }
}