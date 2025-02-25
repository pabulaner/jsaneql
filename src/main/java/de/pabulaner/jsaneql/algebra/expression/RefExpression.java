package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.semana.scope.Entry;

public class RefExpression implements Expression {

    private final Entry entry;

    public RefExpression(Entry entry) {
        this.entry = entry;
    }

    @Override
    public ValueType getType() {
        return entry.getType();
    }
}
