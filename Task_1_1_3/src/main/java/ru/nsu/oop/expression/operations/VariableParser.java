package ru.nsu.oop.expression.operations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VariableParser {
    private VariableParser() {}

    public static Map<String, Integer> parse(String variablesValuesString) {
        if (variablesValuesString == null || variablesValuesString.isBlank()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> variablesValuesMap = new HashMap<>();

        String[] pairs = variablesValuesString.split(";");
        for (String pair: pairs) {
            if (pair.isBlank()) {
                continue;
            }

            String[] keyValue = pair.split("=");
            String variableName = keyValue[0].trim();
            Integer variableValue = Integer.parseInt(keyValue[1].trim());

            variablesValuesMap.put(variableName, variableValue);
        }

        return Collections.unmodifiableMap(variablesValuesMap);
    }
}
