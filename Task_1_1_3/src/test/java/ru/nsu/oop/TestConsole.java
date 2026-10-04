package ru.nsu.oop;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

final class TestConsole {
    private TestConsole() {
    }

    static String captureOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream captured = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(captured);
            try {
                action.run();
                captured.flush();
                return buffer.toString(StandardCharsets.UTF_8);
            } finally {
                System.setOut(original);
            }
        }
    }
}
