package ru.nsu.oop.expression.parsers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс парсера для строк означивающих переменные.
 */
public final class VariableParser {

    private VariableParser() {

    }

    /**
     * Метод парсера строк означивающих переменные.
     *
     * @param variablesValuesString Строка означивающая переменные.
     * @return Мапа вида (имя переменной: значение).
     */
    public static Map<String, Integer> parse(String variablesValuesString) {
        if (variablesValuesString == null || variablesValuesString.isBlank()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> variablesValuesMap = new HashMap<>();

        String[] pairs = variablesValuesString.split(";");
        for (String pair : pairs) {
            if (pair.isBlank()) {
                continue;
            }

            String[] keyValue = pair.split("=");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("Invalid variable assignment format: " + pair);
            }
            String variableName = keyValue[0].trim();
            int variableValue = Integer.parseInt(keyValue[1].trim());

            variablesValuesMap.put(variableName, variableValue);
        }

        return Collections.unmodifiableMap(variablesValuesMap);
    }
}
