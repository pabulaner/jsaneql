package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.schema.value.BooleanValue;
import de.pabulaner.jsaneql.schema.value.DecimalValue;
import de.pabulaner.jsaneql.schema.value.IntegerValue;
import de.pabulaner.jsaneql.schema.value.NullValue;
import de.pabulaner.jsaneql.schema.value.StringValue;

import java.util.Map;

public class ConstExpression implements Expression {

    private final ValueType type;

    private final String value;

    public ConstExpression(ValueType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public Value getValue(Map<IU, Value> row) {
        switch (type) {
            case STRING: return new StringValue(value);
            case INTEGER: return new IntegerValue(Long.parseLong(value));
            case DECIMAL: return new DecimalValue(Double.parseDouble(value));
            case BOOLEAN: return new BooleanValue(Boolean.parseBoolean(value));
            case NULL: return new NullValue();
        }

        return null;
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
