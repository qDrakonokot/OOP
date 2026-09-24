package ru.nsu.oop.expression.bricks;

import java.util.Map;
import ru.nsu.oop.view.Output;

public abstract class Expression {

    protected abstract int calculate(Map<String, Integer> variablesValues);

    public abstract Expression derivate(String variableName);

    protected abstract String asString();


    public final void print(Output output) {
        output.write(asString());
    }

    public final int eval(String variablesValues) {
        return 67;
    }


}
