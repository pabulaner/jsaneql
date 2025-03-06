package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class ExtractExpression implements Expression {

    public enum Part {

        YEAR,
        MONTH,
        DAY,
    }

    private final Expression input;

    private final Part part;

    public ExtractExpression(Expression input, Part part) {
        this.input = input;
        this.part = part;
    }

    @Override
    public Value getValue(Map<IU, Value> row) {
        Value dateValue = input.getValue(row);
        LocalDateTime date = dateValue.getDate();
        long value = 0;

        switch (part) {
            case YEAR: value = date.getYear(); break;
            case MONTH: value = date.getMonthValue(); break;
            case DAY: value = date.getDayOfMonth(); break;
        }

        return Value.ofInteger(value);
    }

    @Override
    public ValueType getType() {
        return ValueType.INTEGER;
    }
}
