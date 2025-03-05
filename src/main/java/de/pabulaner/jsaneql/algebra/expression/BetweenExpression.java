package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.schema.value.BooleanValue;

import java.util.Map;

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
    public Value getValue(Map<IU, Value> row) {
        Value baseValue = base.getValue(row);
        Value lowerValue = lower.getValue(row);
        Value upperValue = upper.getValue(row);

        return new BooleanValue(baseValue.compare(lowerValue) >= 0 && baseValue.compare(upperValue) <= 0);
    }

    @Override
    public ValueType getType() {
        return ValueType.BOOLEAN;
    }
}
