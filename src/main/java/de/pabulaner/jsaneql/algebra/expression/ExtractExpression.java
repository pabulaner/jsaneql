package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;

public class ExtractExpression implements Expression {

    public enum Part {

        YEAR,
        MONTH,
        DAY,
    }

    private final Expression input;

    private final Part part;

    public ExtractExpression(Expression input, Part part) {
        this.input = input;
        this.part = part;
    }

    @Override
    public ValueType getType() {
        return ValueType.INTEGER;
    }
}
