package ru.nsu.oop.expression.operations;

import java.util.Map;
import ru.nsu.oop.expression.bricks.Add;
import ru.nsu.oop.expression.bricks.Expression;
import ru.nsu.oop.expression.bricks.Number;
import ru.nsu.oop.expression.bricks.Variable;

public class ExpressionEvaluator {

    public int evaluate(Expression expr, Map<String, Integer> vars) {
        return switch (expr) {
            case Number n -> n.getValue();
            case Variable v -> vars.get(v.getName());
            case Add a -> evaluate(a.getLeft(), vars) + evaluate(a.getRight(), vars);
            // ...
        };
    }
}
