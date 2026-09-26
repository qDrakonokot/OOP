package ru.nsu.oop.expression.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class VariableParserTest {

    @Test
    void parse_validString_returnsCorrectMap() {
        // Arrange
        String input = "x = 10; y = 13;   z   =  -5  ";

        // Act
        Map<String, Integer> result = VariableParser.parse(input);

        // Assert
        assertEquals(3, result.size());
        assertEquals(10, result.get("x"));
        assertEquals(13, result.get("y"));
        assertEquals(-5, result.get("z"));
    }

    @Test
    void parse_emptyOrNullString_returnsEmptyMap() {
        assertTrue(VariableParser.parse("").isEmpty());
        assertTrue(VariableParser.parse("   ").isEmpty());
        assertTrue(VariableParser.parse(null).isEmpty());
    }

    @Test
    void parse_invalidFormat_throwsException() {
        // Arrange
        String input = "x = 10; y = ;"; // Нет значения для y

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> VariableParser.parse(input));
    }

    @Test
    void parse_stringWithEmptyBlocks_ignoresThem() {
        // Две точки с запятой подряд и пробелы в конце
        String input = "x = 10;; y = 20;  ";

        Map<String, Integer> result = VariableParser.parse(input);

        assertEquals(2, result.size());
        assertEquals(10, result.get("x"));
        assertEquals(20, result.get("y"));
    }
}