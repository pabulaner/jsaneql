package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.schema.value.BooleanValue;
import de.pabulaner.jsaneql.schema.value.StringValue;

import java.util.Map;

public class BinaryExpression implements Expression {

    public enum Operation {

        ADD,
        SUB,
        MUL,
        DIV,
        MOD,
        POW,
        EQUALS,
        NOT_EQUALS,
        LESS,
        GREATER,
        LESS_EQUALS,
        GREATER_EQUALS,
        LIKE,
        AND,
        OR,
    }

    private final ValueType type;

    private final Operation operation;

    private final Expression left;

    private final Expression right;

    public BinaryExpression(ValueType type, Operation operation, Expression left, Expression right) {
        this.type = type;
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    @Override
    public Value getValue(Map<IU, Value> row) {
        Value leftValue = left.getValue(row);
        Value rightValue = right.getValue(row);
        
        switch (operation) {
            case ADD: return leftValue.add(rightValue);
            case SUB: return leftValue.sub(rightValue);
            case MUL: return leftValue.mul(rightValue);
            case DIV: return leftValue.div(rightValue);
            case MOD: return leftValue.mod(rightValue);
            case POW: return leftValue.pow(rightValue);
            case EQUALS: return new BooleanValue(leftValue.compare(rightValue) == 0);
            case NOT_EQUALS: return new BooleanValue(leftValue.compare(rightValue) != 0);
            case LESS: return new BooleanValue(leftValue.compare(rightValue) < 0);
            case GREATER: return new BooleanValue(leftValue.compare(rightValue) > 0);
            case LESS_EQUALS: return new BooleanValue(leftValue.compare(rightValue) <= 0);
            case GREATER_EQUALS: return new BooleanValue(leftValue.compare(rightValue) >= 0);
            case LIKE: return new BooleanValue(((StringValue) leftValue).like(rightValue));
            case AND: return new BooleanValue(leftValue.getBoolean() && rightValue.getBoolean());
            case OR: return new BooleanValue(leftValue.getBoolean() || rightValue.getBoolean());
        }

        return null;
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
