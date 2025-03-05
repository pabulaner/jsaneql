package de.pabulaner.jsaneql.schema.value;

import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

public class NullValue extends Value {


    public NullValue() {
        super(ValueType.NULL, null);
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
        return 0;
    }
}
