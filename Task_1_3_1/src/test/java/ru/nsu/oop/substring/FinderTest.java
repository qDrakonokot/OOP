package ru.nsu.oop.substring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
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
    void find_emptyOrNullTarget_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> Finder.find(new StringReader("text"), ""));
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
}