package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.schema.value.BooleanValue;

import java.util.List;
import java.util.Map;

public class InExpression implements Expression {

    private final Expression probe;

    private final List<Expression> values;

    public InExpression(Expression probe, List<Expression> values) {
        this.probe = probe;
        this.values = values;
    }

    @Override
    public Value getValue(Map<IU, Value> row) {
        boolean result = false;
        Value probeValue = probe.getValue(row);

        for (Expression value : values) {
            result |= probeValue.compare(value.getValue(row)) == 0;
        }

        return new BooleanValue(result);
    }

    @Override
    public ValueType getType() {
        return probe.getType();
    }
}
