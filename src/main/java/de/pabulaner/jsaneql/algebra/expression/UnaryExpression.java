package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.Map;

public class UnaryExpression implements Expression {

    public enum Operation {

        ADD,
        SUB,
        NOT,
    }

    private final Operation operation;

    private final Expression expression;

    public UnaryExpression(Operation operation, Expression expression) {
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    public Value getValue(Map<IU, Value> row) {
        Value value = expression.getValue(row);

        switch (operation) {
            case ADD: return value;
            case SUB: return Value.ofInteger(0L).sub(value);
            case NOT: return Value.ofBoolean(!value.getBoolean());
        }

        return null;
    }

    @Override
    public ValueType getType() {
        return expression.getType();
    }
}
