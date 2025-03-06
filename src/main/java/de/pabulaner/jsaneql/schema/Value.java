package de.pabulaner.jsaneql.schema;

import de.pabulaner.jsaneql.exception.IllegalTypeException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Value {

    private static final Map<Class<?>, ValueType> TYPE_MAP = new HashMap<Class<?>, ValueType>() {{
        put(String.class, ValueType.STRING);
        put(Long.class, ValueType.INTEGER);
        put(Double.class, ValueType.DECIMAL);
        put(Boolean.class, ValueType.BOOLEAN);
        put(Date.class, ValueType.DATE);
        put(Duration.class, ValueType.INTERVAL);
    }};

    private final ValueType type;

    private final Object value;

    private Value(ValueType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public static Value ofString(String value) {
        return new Value(ValueType.STRING, value);
    }

    public static Value ofInteger(Long value) {
        return new Value(ValueType.INTEGER, value);
    }

    public static Value ofDecimal(Double value) {
        return new Value(ValueType.DECIMAL, value);
    }

    public static Value ofBoolean(Boolean value) {
        return new Value(ValueType.BOOLEAN, value);
    }

    public static Value ofDate(LocalDateTime value) {
        return new Value(ValueType.DATE, value);
    }

    public static Value ofInterval(Period value) {
        return new Value(ValueType.INTERVAL, value);
    }

    public static Value ofNull() {
        return new Value(ValueType.NULL, null);
    }

    public Value add(Value other) {
        if (type == ValueType.STRING && other.type == ValueType.STRING) {
            return ofString(getString() + other.getString());
        }

        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                return ofDecimal(getDecimal() + other.getDecimal());
            } else {
                return ofInteger(getInteger() + other.getInteger());
            }
        }

        if (type == ValueType.DATE && other.type == ValueType.INTERVAL) {
            return ofDate(getDate().plus(other.getInterval()));
        }

        throw new IllegalArgumentException();
    }

    public Value sub(Value other) {
        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                return ofDecimal(getDecimal() - other.getDecimal());
            } else {
                return ofInteger(getInteger() - other.getInteger());
            }
        }

        if (type == ValueType.DATE && other.type == ValueType.INTERVAL) {
            return ofDate(getDate().minus(other.getInterval()));
        }

        throw new IllegalArgumentException();
    }

    public Value mul(Value other) {
        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                return ofDecimal(getDecimal() * other.getDecimal());
            } else {
                return ofInteger(getInteger() * other.getInteger());
            }
        }

        throw new IllegalArgumentException();
    }

    public Value div(Value other) {
        if (isNumeric() && other.isNumeric()) {
            return ofDecimal(getDecimal() / other.getDecimal());
        }

        throw new IllegalArgumentException();
    }

    public Value mod(Value other) {
        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                return ofDecimal(getDecimal() % other.getDecimal());
            } else {
                return ofInteger(getInteger() % other.getInteger());
            }
        }

        throw new IllegalArgumentException();
    }

    public Value pow(Value other) {
        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                return ofDecimal(Math.pow(getDecimal(), other.getDecimal()));
            } else {
                return ofInteger((long) Math.pow(getInteger(), other.getInteger()));
            }
        }

        throw new IllegalArgumentException();
    }

    public Value like(Value other) {
        if (type == ValueType.STRING && other.type == ValueType.STRING) {
            String pattern = other.getString().replace("%", ".*");
            return Value.ofBoolean(Pattern.compile(pattern).matcher(getString()).matches());
        }

        throw new IllegalArgumentException();
    }

    public int compare(Value other) {
        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                double value = getDecimal() - other.getDecimal();

                if (value < 0) return -1;
                if (value > 0) return 1;
            } else {
                long value = getInteger() - other.getInteger();

                if (value < 0) return -1;
                if (value > 0) return 1;
            }

            return 0;
        }

        if (type != other.type) {
            return 1;
        }

        switch (type) {
            case STRING: return getString().compareTo(other.getString());
            case BOOLEAN: return (getBoolean() ? 1 : 0) - (other.getBoolean() ? 1 : 0);
            case DATE: return getDate().compareTo(other.getDate());
            case INTERVAL: return LocalDate.MIN.plus(getInterval()).compareTo(LocalDate.MIN.plus(other.getInterval()));
            case NULL: return 0;
        }

        throw new IllegalArgumentException();
    }

    private boolean isNumeric() {
        return type == ValueType.INTEGER || type == ValueType.DECIMAL;
    }

    public ValueType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public String getString() {
        if (type == ValueType.STRING) {
            return (String) value;
        }

        throw new IllegalTypeException();
    }

    public long getInteger() {
        if (type == ValueType.INTEGER) {
            return (long) value;
        }

        if (type == ValueType.DECIMAL) {
            return (long) (double) value;
        }

        throw new IllegalTypeException();
    }

    public double getDecimal() {
        if (type == ValueType.INTEGER) {
            return (double) (long) value;
        }

        if (type == ValueType.DECIMAL) {
            return (double) value;
        }

        throw new IllegalTypeException();
    }

    public boolean getBoolean() {
        if (type == ValueType.BOOLEAN) {
            return (boolean) value;
        }

        throw new IllegalTypeException();
    }

    public LocalDateTime getDate() {
        if (type == ValueType.DATE) {
            return (LocalDateTime) value;
        }

        throw new IllegalTypeException();
    }

    public Period getInterval() {
        if (type == ValueType.INTERVAL) {
            return (Period) value;
        }

        throw new IllegalTypeException();
    }
}
