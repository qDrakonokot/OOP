package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class MainTest {
    @Test
    @ResourceLock(Resources.SYSTEM_OUT)
    @DisplayName("Демонстрационный Main выводит число, вложенную сумму и результат 23")
    void runsDemonstration() {
        String newline = System.lineSeparator();
        String expected = "5" + newline + "(1+(3+2))" + newline + "23" + newline;
        assertEquals(expected, TestConsole.captureOutput(() -> Main.main(new String[0])));
    }
}
