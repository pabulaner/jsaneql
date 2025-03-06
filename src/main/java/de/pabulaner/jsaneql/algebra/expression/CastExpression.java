package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueCast;
import de.pabulaner.jsaneql.schema.ValueType;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class CastExpression implements Expression {

    private final Expression value;

    private final ValueType type;

    public CastExpression(Expression value, ValueType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Value getValue(Map<IU, Value> row) {
        return ValueCast.cast(value.getValue(row), type);
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
