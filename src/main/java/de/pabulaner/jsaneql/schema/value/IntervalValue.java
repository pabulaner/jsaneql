package de.pabulaner.jsaneql.schema.value;

import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

import java.time.Duration;

public class IntervalValue extends Value {

    public IntervalValue(Duration value) {
        super(ValueType.INTERVAL, value);
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
        if (other.getType() == ValueType.INTERVAL) {
            return getInterval().compareTo(other.getInterval());
        }

        throw new IllegalArgumentException();
    }
}
