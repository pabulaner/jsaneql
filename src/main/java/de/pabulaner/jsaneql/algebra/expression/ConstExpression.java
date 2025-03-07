package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

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
            case TEXT: return Value.ofString(value);
            case INTEGER: return Value.ofInteger(Long.parseLong(value));
            case DECIMAL: return Value.ofDecimal(Double.parseDouble(value));
            case BOOLEAN: return Value.ofBoolean(Boolean.parseBoolean(value));
            case NULL: return Value.ofNull();
        }

        return null;
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
