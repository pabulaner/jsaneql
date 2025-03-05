package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.schema.value.StringValue;

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
        Value valueValue = value.getValue(row);
        Value fromValue = from.getValue(row);
        Value lengthValue = length.getValue(row);

        return new StringValue(valueValue.getString().substring((int) fromValue.getInteger() - 1, (int) fromValue.add(lengthValue).getInteger() - 1));
    }

    @Override
    public ValueType getType() {
        return ValueType.STRING;
    }
}
