package de.pabulaner.jsaneql.schema.value;

import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.Date;

public class DateValue extends Value {

    public DateValue(Date value) {
        super(ValueType.DATE, value);
    }

    @Override
    public Value add(Value other) {
        if (other.getType() == ValueType.INTERVAL) {
            return new DateValue(Date.from(getDate().toInstant().plus(other.getInterval())));
        }

        return null;
    }

    @Override
    public Value sub(Value other) {
        if (other.getType() == ValueType.INTERVAL) {
            return new DateValue(Date.from(getDate().toInstant().minus(other.getInterval())));
        }

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
        if (other.getType() == ValueType.DATE) {
            return getDate().compareTo(other.getDate());
        }

        throw new IllegalArgumentException();
    }
}
