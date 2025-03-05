package de.pabulaner.jsaneql.schema.value;

import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

public class BooleanValue extends Value {

    public BooleanValue(boolean value) {
        super(ValueType.BOOLEAN, value);
    }

    @Override
    public Value add(Value other) {
        return null;
    }

    @Override
    public Value sub(Value other) {
        return null;
    }

    @Override
    public Value mul(Value other) {
        return null;
    }

    @Override
    public Value div(Value other) {
        return null;
    }

    @Override
    public Value mod(Value other) {
        return null;
    }

    @Override
    public Value pow(Value other) {
        return null;
    }

    @Override
    public int compare(Value other) {
        if (other.getType() == ValueType.BOOLEAN) {
            int v1 = getBoolean() ? 1 : 0;
            int v2 = other.getBoolean() ? 1 : 0;

            return v1 - v2;
        }

        throw new IllegalArgumentException();
    }
}
