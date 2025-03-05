package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;

public class SubstrExpression implements Expression {

    private final Expression value;

    private final Expression from;

    private final Expression length;

    public SubstrExpression(Expression value, Expression from, Expression length) {
        this.value = value;
        this.from = from;
        this.length = length;
    }

    @Override
    public ValueType getType() {
        return ValueType.STRING;
    }
}
