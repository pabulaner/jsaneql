package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;

public class ConstExpression implements Expression {

    private final ValueType type;

    private final String value;

    public ConstExpression(ValueType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
