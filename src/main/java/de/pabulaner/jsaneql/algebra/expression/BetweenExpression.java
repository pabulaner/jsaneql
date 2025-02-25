package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;

public class BetweenExpression implements Expression {

    private final Expression base;

    private final Expression lower;

    private final Expression upper;

    public BetweenExpression(Expression base, Expression lower, Expression upper) {
        this.base = base;
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public ValueType getType() {
        return ValueType.BOOLEAN;
    }
}
