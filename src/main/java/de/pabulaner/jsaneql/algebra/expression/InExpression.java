package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;

import java.util.List;

public class InExpression implements Expression {

    private final Expression probe;

    private final List<Expression> values;

    public InExpression(Expression probe, List<Expression> values) {
        this.probe = probe;
        this.values = values;
    }

    @Override
    public ValueType getType() {
        return probe.getType();
    }
}
