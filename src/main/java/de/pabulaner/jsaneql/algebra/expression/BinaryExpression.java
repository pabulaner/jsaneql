package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.schema.ValueType;

public class BinaryExpression implements Expression {

    public enum Operation {

        ADD,
        SUB,
        MUL,
        DIV,
        MOD,
        POW,
        EQ,
        NEQ,
        LT,
        GT,
        LE,
        GE,
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
    public ValueType getType() {
        return type;
    }
}
