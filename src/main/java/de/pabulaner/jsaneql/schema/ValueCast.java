package de.pabulaner.jsaneql.schema;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class ValueCast {

    private ValueCast() {
    }

    public static Value cast(Value value, ValueType type) {
        switch (type) {
            case STRING: return Value.ofString(castToString(value));
            case INTEGER: return Value.ofInteger(castToInteger(value));
            case DECIMAL: return Value.ofDecimal(castToDecimal(value));
            case BOOLEAN: return Value.ofBoolean(castToBoolean(value));
            case DATE: return Value.ofDate(castToDate(value));
            case INTERVAL: return Value.ofInterval(castToInterval(value));
        }

        return null;
    }

    private static String castToString(Value value) {
        return value.getType() != ValueType.NULL
                ? value.getValue().toString()
                : null;
    }

    private static Long castToInteger(Value value) {
        switch (value.getType()) {
            case STRING: return Long.parseLong(value.getString());
            case INTEGER:
            case DECIMAL: return value.getInteger();
            case BOOLEAN: return value.getBoolean() ? 1L : 0L;
        }

        return null;
    }

    private static Double castToDecimal(Value value) {
        switch (value.getType()) {
            case STRING: return Double.valueOf(value.getString());
            case INTEGER:
            case DECIMAL: value.getDecimal();
            case BOOLEAN: return value.getBoolean() ? 1.0 : 0.0;
        }

        return null;
    }

    private static Boolean castToBoolean(Value value) {
        switch (value.getType()) {
            case STRING:
                if (value.getString().equals("false") || value.getString().equals("0")) {
                    return false;
                }

                if (value.getString().equals("true") || value.getString().equals("1")) {
                    return true;
                }

                break;
            case INTEGER: return value.getInteger() != 0;
            case DECIMAL: return value.getDecimal() != 0;
            case BOOLEAN: return value.getBoolean();
        }

        return null;
    }

    private static LocalDateTime castToDate(Value value) {
        switch (value.getType()) {
            case STRING: return LocalDateTime.parse(value.getString(), new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd[ HH:mm]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .toFormatter());
            case DATE: return value.getDate();
        }

        return null;
    }

    private static Period castToInterval(Value value) {
        switch (value.getType()) {
            case STRING:
                String[] parts = value.getString().split(" ");

                if (parts.length == 2) {
                    int amount = Integer.parseInt(parts[0]);

                    switch (parts[1]) {
                        case "year":
                        case "years": return Period.ofYears(amount);
                        case "month":
                        case "months": return Period.ofMonths(amount);
                        case "day":
                        case "days": return Period.ofDays(amount);
                    }
                }

                break;
            case INTERVAL: return value.getInterval();
        }

        return null;
    }
}
