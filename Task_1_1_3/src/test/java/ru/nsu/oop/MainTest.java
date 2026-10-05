package ru.nsu.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MainTest {
    @ParameterizedTest
    @CsvSource({
        "5, '', 5",
        "'(5+2)', '', 7",
        "'((x*2)+-3)', 'x = 5', 7",
        "-2147483648, '', -2147483648",
        "'(x+y)', 'x = 5; y = 13', 18",
        "'(5*2)', '   ', 10"
    })
    @ResourceLock(Resources.SYSTEM_OUT)
    @ResourceLock("java.lang.System.in")
    @DisplayName("Main читает выражение и означивание, затем выводит результат")
    void readsAndEvaluatesExpression(String expression, String assignments, int expected) {
        InputStream original = System.in;
        byte[] input = ("  " + expression + "  \n" + assignments + "\n")
                .getBytes(StandardCharsets.UTF_8);
        try {
            System.setIn(new ByteArrayInputStream(input));
            String output = TestConsole.captureOutput(() -> Main.main(new String[0]));
            assertEquals(expected + System.lineSeparator(), output);
        } finally {
            System.setIn(original);
        }
    }
}
