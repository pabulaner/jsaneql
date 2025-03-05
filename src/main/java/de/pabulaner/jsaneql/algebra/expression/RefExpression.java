package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.schema.value.StringValue;

import java.util.Map;

public class RefExpression implements Expression {

    private final IU iu;

    public RefExpression(IU iu) {
        this.iu = iu;
    }

    @Override
    public Value getValue(Map<IU, Value> row) {
        return row.get(iu);
    }

    @Override
    public ValueType getType() {
        return iu.getType();
    }
}
