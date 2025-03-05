package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.Map;

public class EmptyExpression implements Expression {

    @Override
    public Value getValue(Map<IU, Value> row) {
        return null;
    }

    @Override
    public ValueType getType() {
        return null;
    }
}
