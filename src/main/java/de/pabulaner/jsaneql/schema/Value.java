package de.pabulaner.jsaneql.schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;
import java.util.regex.Pattern;

public class Value {

    private final ValueType type;

    private final Object value;

    private Value(ValueType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public static Value ofString(String value) {
        return new Value(ValueType.TEXT, value);
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
        return new Value(ValueType.UNKNOWN, null);
    }

    public Value add(Value other) {
        if (type == ValueType.TEXT && other.type == ValueType.TEXT) {
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

        throw new IllegalStateException();
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

        throw new IllegalStateException();
    }

    public Value mul(Value other) {
        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                return ofDecimal(getDecimal() * other.getDecimal());
            } else {
                return ofInteger(getInteger() * other.getInteger());
            }
        }

        throw new IllegalStateException();
    }

    public Value div(Value other) {
        if (isNumeric() && other.isNumeric()) {
            return ofDecimal(getDecimal() / other.getDecimal());
        }

        throw new IllegalStateException();
    }

    public Value mod(Value other) {
        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                return ofDecimal(getDecimal() % other.getDecimal());
            } else {
                return ofInteger(getInteger() % other.getInteger());
            }
        }

        throw new IllegalStateException();
    }

    public Value pow(Value other) {
        if (isNumeric() && other.isNumeric()) {
            if (type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
                return ofDecimal(Math.pow(getDecimal(), other.getDecimal()));
            } else {
                return ofInteger((long) Math.pow(getInteger(), other.getInteger()));
            }
        }

        throw new IllegalStateException();
    }

    public Value like(Value other) {
        if (type == ValueType.TEXT && other.type == ValueType.TEXT) {
            String pattern = other.getString().replace("%", ".*");
            return Value.ofBoolean(Pattern.compile(pattern).matcher(getString()).matches());
        }

        throw new IllegalStateException();
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
            case TEXT: return getString().compareTo(other.getString());
            case BOOLEAN: return (getBoolean() ? 1 : 0) - (other.getBoolean() ? 1 : 0);
            case DATE: return getDate().compareTo(other.getDate());
            case INTERVAL: return LocalDate.MIN.plus(getInterval()).compareTo(LocalDate.MIN.plus(other.getInterval()));
            case UNKNOWN: return 0;
        }

        throw new IllegalStateException();
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
        if (type == ValueType.TEXT) {
            return (String) value;
        }

        throw new IllegalStateException();
    }

    public long getInteger() {
        if (type == ValueType.INTEGER) {
            return (long) value;
        }

        if (type == ValueType.DECIMAL) {
            return (long) (double) value;
        }

        throw new IllegalStateException();
    }

    public double getDecimal() {
        if (type == ValueType.INTEGER) {
            return (double) (long) value;
        }

        if (type == ValueType.DECIMAL) {
            return (double) value;
        }

        throw new IllegalStateException();
    }

    public boolean getBoolean() {
        if (type == ValueType.BOOLEAN) {
            return (boolean) value;
        }

        throw new IllegalStateException();
    }

    public LocalDateTime getDate() {
        if (type == ValueType.DATE) {
            return (LocalDateTime) value;
        }

        throw new IllegalStateException();
    }

    public Period getInterval() {
        if (type == ValueType.INTERVAL) {
            return (Period) value;
        }

        throw new IllegalStateException();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof Value) {
            Value casted = (Value) obj;

            if (casted.value == null || value == null) {
                return casted.value == null && value == null;
            }

            return casted.value.equals(value);
        }

        return false;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : null;
    }
}
