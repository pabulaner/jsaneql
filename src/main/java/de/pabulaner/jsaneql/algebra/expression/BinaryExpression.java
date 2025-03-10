package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.ValueType;

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
        CONCAT,
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
    public void generate(SQLWriter out) {
        left.generate(out);

        switch (operation) {
            case ADD: out.write(" + "); break;
            case SUB: out.write(" - "); break;
            case MUL: out.write(" * "); break;
            case DIV: out.write(" / "); break;
            case MOD: out.write(" % "); break;
            case POW: out.write(" ^ "); break;
            case EQUALS: out.write(" = "); break;
            case NOT_EQUALS: out.write(" <> "); break;
            case LESS: out.write(" < "); break;
            case GREATER: out.write(" > "); break;
            case LESS_EQUALS: out.write(" <= "); break;
            case GREATER_EQUALS: out.write(" >= "); break;
            case LIKE: out.write(" LIKE "); break;
            case CONCAT: out.write(" || "); break;
            case AND: out.write(" AND "); break;
            case OR: out.write(" OR "); break;
        }

        right.generate(out);
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
