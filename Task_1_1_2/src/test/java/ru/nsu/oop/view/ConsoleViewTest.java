package ru.nsu.oop.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.oop.model.Hand;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleViewTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testShowMessage() {
        // Подготавливаем поток ввода-вывода
        System.setIn(new ByteArrayInputStream("".getBytes()));
        ConsoleView view = new ConsoleView();

        view.showMessage("Тестовое сообщение");

        // Проверяем, что сообщение вывелось в консоль с переносом строки
        assertEquals("Тестовое сообщение" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testAskPlayerMoveTakesCard() {
        // Имитируем ввод пользователя "1"
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        ConsoleView view = new ConsoleView();

        // Метод должен вернуть true при вводе 1
        assertTrue(view.askPlayerMove());
    }

    @Test
    void testAskPlayAgainExits() {
        // Имитируем ввод пользователя "0"
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ConsoleView view = new ConsoleView();

        // Метод должен вернуть false при вводе 0
        assertFalse(view.askPlayAgain());
    }

    @Test
    void testShowCards() {
        System.setIn(new ByteArrayInputStream("".getBytes()));
        ConsoleView view = new ConsoleView();
        Hand hand = new Hand();

        // Метод не должен падать при вызове
        view.showCards("Игрок", hand);
        assertTrue(outContent.toString().contains("Игрок:"));
    }

    @Test
    void testAskPlayerMoveStops() {
        // Имитируем ввод "0"
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        ConsoleView view = new ConsoleView();

        assertFalse(view.askPlayerMove());
    }

    @Test
    void testAskPlayAgainYes() {
        // Имитируем ввод "1"
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        ConsoleView view = new ConsoleView();

        assertTrue(view.askPlayAgain());
    }
}