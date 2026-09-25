package ru.nsu.oop.expression.bricks;

import java.util.Map;
import ru.nsu.oop.output.Output;

public abstract class Expression {

    protected abstract int calculate(Map<String, Integer> variablesValues);

    public abstract Expression derivative(String variableName);

    @Override
    public abstract String toString();

    public final void print(Output output) {
        output.write(this.toString());
    }

    public final int eval(String variablesValues) {
        return 67;
    }


}
