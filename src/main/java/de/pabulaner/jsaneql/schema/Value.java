package de.pabulaner.jsaneql.schema;

import java.time.Duration;
import java.util.Date;

public abstract class Value {

    private final ValueType type;

    private final Object value;

    protected Value(ValueType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public abstract Value add(Value other);

    public abstract Value sub(Value other);

    public abstract Value mul(Value other);

    public abstract Value div(Value other);

    public abstract Value mod(Value other);

    public abstract Value pow(Value other);

    public abstract int compare(Value other);

    public String getString() {
        return (String) value;
    }

    public long getInteger() {
        return (long) value;
    }

    public double getDecimal() {
        return (double) value;
    }

    public boolean getBoolean() {
        return false;
    }

    public Date getDate() {
        return (Date) value;
    }

    public Duration getInterval() {
        return (Duration) value;
    }

    public ValueType getType() {
        return type;
    }
}
