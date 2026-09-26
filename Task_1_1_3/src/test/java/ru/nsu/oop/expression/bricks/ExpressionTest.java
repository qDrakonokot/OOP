package ru.nsu.oop.expression.bricks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.oop.output.Output;

class ExpressionTest {

    @Test
    void print_writesToStringOutput() {
        // Arrange
        Expression expr = new Number(42);
        FakeOutput fakeOutput = new FakeOutput();

        // Act
        expr.print(fakeOutput);

        // Assert
        assertEquals("42", fakeOutput.writtenText);
    }

    @Test
    void eval_parsesStringAndCalculates() {
        // Arrange: создаем дерево (x+5)
        Expression expr = new Add(new Variable("x"), new Number(5));

        // Act
        int result = expr.eval("x = 10");

        // Assert
        assertEquals(15, result);
    }

    // Fake-реализация для перехвата вывода
    private static class FakeOutput implements Output {

        String writtenText = "";

        @Override
        public void write(String text) {
            this.writtenText = text;
        }
    }

    @Test
    void print_noArgs_writesToSystemOut() {
        // Arrange
        Expression expr = new Number(99);

        // Перехватываем System.out
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        try {
            // Act
            expr.print();

            // Assert (проверяем, что в консоль ушло "99" + перенос строки)
            assertEquals("99" + System.lineSeparator(), outContent.toString());
        } finally {
            // Обязательно возвращаем System.out на место, чтобы не сломать другие тесты!
            System.setOut(originalOut);
        }
    }
}