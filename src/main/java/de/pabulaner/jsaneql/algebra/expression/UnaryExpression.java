package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.ValueType;

public class UnaryExpression implements Expression {

    public enum Operation {

        ADD,
        SUB,
        NOT,
    }

    private final Operation operation;

    private final Expression expression;

    public UnaryExpression(Operation operation, Expression expression) {
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    public void generate(SQLWriter out) {
        switch (operation) {
            case ADD: out.write("+"); break;
            case SUB: out.write("-"); break;
            case NOT: out.write("!"); break;
        }

        expression.generateOperand(out);
    }

    @Override
    public ValueType getType() {
        return expression.getType();
    }
}
