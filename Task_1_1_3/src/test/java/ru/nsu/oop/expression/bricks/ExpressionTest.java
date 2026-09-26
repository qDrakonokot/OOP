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
}