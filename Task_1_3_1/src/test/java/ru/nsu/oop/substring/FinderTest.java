package ru.nsu.oop.substring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FinderTest {

    @TempDir
    Path tempDir; // JUnit сам создаст и удалит эту папку

    @Test
    void find_basicString_returnsCorrectIndices() throws IOException {
        String text = "абракадабра";
        String target = "бра";

        List<Integer> result = Finder.find(new StringReader(text), target);

        assertEquals(List.of(1, 8), result);
    }

    @Test
    void find_overlappingMatches_returnsAllIndices() throws IOException {
        String text = "aaaa";
        String target = "aa";

        List<Integer> result = Finder.find(new StringReader(text), target);

        assertEquals(List.of(0, 1, 2), result);
    }

    @Test
    void find_targetLargerThanText_returnsEmptyList() throws IOException {
        String text = "Да";
        String target = "Привет";

        List<Integer> result = Finder.find(new StringReader(text), target);

        assertTrue(result.isEmpty());
    }

    @Test
    void find_noMatches_returnsEmptyList() throws IOException {
        String text = "Hello World";
        String target = "Java";

        List<Integer> result = Finder.find(new StringReader(text), target);

        assertTrue(result.isEmpty());
    }

    @Test
    void find_emptyOrNullTarget_throwsException() throws IOException {
        List<Integer> result = Finder.find(new StringReader("text"), "");

        assertEquals(Collections.emptyList(), result);
        assertThrows(IllegalArgumentException.class,
            () -> Finder.find(new StringReader("text"), null));
    }

    @Test
    void findInFile_validFile_returnsCorrectIndices() throws IOException {
        Path tempFile = tempDir.resolve("input.txt");
        Files.writeString(tempFile, "абракадабра");

        List<Integer> result = Finder.find(tempFile.toString(), "бра");

        assertEquals(List.of(1, 8), result);
    }

    @Test
    void findInFile_fileNotFound_throwsIOException() {
        String fakePath = tempDir.resolve("ghost.txt").toString();

        assertThrows(IOException.class, () -> Finder.find(fakePath, "бра"));
    }

    @Test
    void findInFile_emptyFilename_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Finder.find("", "бра"));
        assertThrows(IllegalArgumentException.class, () -> Finder.find((String) null, "бра"));
    }

    @Test
    void find_hugeGeneratedData_doesNotThrowOutOfMemory() throws IOException {
        // 12 сек на 1 Гб. На компе выполнялось, прикольный бенчмарк
        // 116 сек на 10 Гб. Видно, что рост линейный

        long size = 100_000_000_0L;
        String target = "магия";
        long targetPosition = size - target.length() - 10;

        List<Integer> result = Finder.find(new HugeTextReader(size, target, targetPosition),
            target);

        assertEquals(1, result.size());
        assertEquals((int) targetPosition, result.get(0));
    }

    private static class HugeTextReader extends java.io.Reader {

        private final long totalCharsToGenerate;
        private final String target;
        private final long targetPosition;
        private long charsGenerated = 0;

        public HugeTextReader(long totalCharsToGenerate, String target, long targetPosition) {
            this.totalCharsToGenerate = totalCharsToGenerate;
            this.target = target;
            this.targetPosition = targetPosition;
        }

        @Override
        public int read(char[] cbuf, int off, int len) {
            // Этот метод требует интерфейс Reader, но наш алгоритм использует read()
            // по одному символу.
            // Поэтому оставляем заглушку.
            throw new UnsupportedOperationException("Use read() instead");
        }

        @Override
        public int read() {
            if (charsGenerated >= totalCharsToGenerate) {
                return -1;
            }

            if (charsGenerated >= targetPosition
                && charsGenerated < targetPosition + target.length()) {
                int charIndex = (int) (charsGenerated - targetPosition);
                charsGenerated++;
                return target.charAt(charIndex);
            }

            charsGenerated++;
            return 'a';
        }

        @Override
        public void close() {

        }
    }
}