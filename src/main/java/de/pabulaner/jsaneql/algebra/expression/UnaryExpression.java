package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;

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
    public ValueType getType() {
        return expression.getType();
    }
}
