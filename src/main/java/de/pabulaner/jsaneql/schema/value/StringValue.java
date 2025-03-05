package de.pabulaner.jsaneql.schema.value;

import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

public class StringValue extends Value {

    public StringValue(String value) {
        super(ValueType.STRING, value);
    }

    @Override
    public Value add(Value other) {
        if (other.getType() == ValueType.STRING) {
            return new StringValue(getString() + other.getString());
        }

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
        if (other.getType() == ValueType.STRING) {
            return getString().compareTo(other.getString());
        }

        throw new IllegalArgumentException();
    }

    public boolean like(Value other) {
        return false;
    }
}
