package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.schema.value.IntegerValue;

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
        Date date = dateValue.getDate();
        Calendar calendar = new GregorianCalendar();
        long value = 0;

        calendar.setTime(date);

        switch (part) {
            case YEAR: value = calendar.get(Calendar.YEAR); break;
            case MONTH: value = calendar.get(Calendar.MONTH); break;
            case DAY: value = calendar.get(Calendar.DAY_OF_MONTH); break;
        }

        return new IntegerValue(value);
    }

    @Override
    public ValueType getType() {
        return ValueType.INTEGER;
    }
}
