package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.Map;

public class SubstrExpression implements Expression {

    private final Expression value;

    private final Expression from;

    private final Expression length;

    public SubstrExpression(Expression value, Expression from, Expression length) {
        this.value = value;
        this.from = from;
        this.length = length;
    }

    @Override
    public Value getValue(Map<IU, Value> row) {
        Value fromValue = from.getValue(row);
        Value lengthValue = length.getValue(row);

        int begin = (int) fromValue.getInteger() - 1;
        int end = (int) fromValue.add(lengthValue).getInteger() - 1;

        return Value.ofString(value.getValue(row).getString().substring(begin, end));
    }

    @Override
    public ValueType getType() {
        return ValueType.TEXT;
    }
}
