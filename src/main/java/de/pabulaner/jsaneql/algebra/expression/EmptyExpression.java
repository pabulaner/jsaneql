package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;

public class EmptyExpression implements Expression {

    @Override
    public ValueType getType() {
        return null;
    }
}
