package de.pabulaner.jsaneql.schema.value;

import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

public class IntegerValue extends Value {

    public IntegerValue(long value) {
        super(ValueType.INTEGER, value);
    }

    @Override
    public Value add(Value other) {
        if (other.getType() == ValueType.INTEGER) {
            return new IntegerValue(getInteger() + other.getInteger());
        }

        if (other.getType() == ValueType.DECIMAL) {
            return new DecimalValue(getDecimal() + other.getDecimal());
        }

        return null;
    }

    @Override
    public Value sub(Value other) {
        if (other.getType() == ValueType.INTEGER) {
            return new IntegerValue(getInteger() - other.getInteger());
        }

        if (other.getType() == ValueType.DECIMAL) {
            return new DecimalValue(getDecimal() - other.getDecimal());
        }

        return null;
    }

    @Override
    public Value mul(Value other) {
        if (other.getType() == ValueType.INTEGER) {
            return new IntegerValue(getInteger() * other.getInteger());
        }

        if (other.getType() == ValueType.DECIMAL) {
            return new DecimalValue(getDecimal() * other.getDecimal());
        }

        return null;
    }

    @Override
    public Value div(Value other) {
        if (other.getType() == ValueType.INTEGER) {
            return new IntegerValue(getInteger() / other.getInteger());
        }

        if (other.getType() == ValueType.DECIMAL) {
            return new DecimalValue(getDecimal() / other.getDecimal());
        }

        return null;
    }

    @Override
    public Value mod(Value other) {
        if (other.getType() == ValueType.INTEGER) {
            return new IntegerValue(getInteger() % other.getInteger());
        }

        if (other.getType() == ValueType.DECIMAL) {
            return new DecimalValue(getDecimal() % other.getDecimal());
        }

        return null;
    }

    @Override
    public Value pow(Value other) {
        if (other.getType() == ValueType.INTEGER) {
            return new IntegerValue((long) Math.pow(getInteger(), other.getInteger()));
        }

        if (other.getType() == ValueType.DECIMAL) {
            return new DecimalValue(Math.pow(getDecimal(), other.getDecimal()));
        }

        return null;
    }

    @Override
    public int compare(Value other) {
        if (other.getType() == ValueType.INTEGER) {
            long v = getInteger() - other.getInteger();

            if (v < 0) return -1;
            if (v > 0) return 1;

            return 0;
        }

        if (other.getType() == ValueType.DECIMAL) {
            double v = getDecimal() - other.getDecimal();

            if (v < 0) return -1;
            if (v > 0) return 1;

            return 0;
        }

        throw new IllegalArgumentException();
    }
}
