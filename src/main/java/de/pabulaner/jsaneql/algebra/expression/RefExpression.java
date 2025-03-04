package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.ValueType;

public class RefExpression implements Expression {

    private final IU iu;

    public RefExpression(IU iu) {
        this.iu = iu;
    }

    @Override
    public ValueType getType() {
        return iu.getType();
    }
}
