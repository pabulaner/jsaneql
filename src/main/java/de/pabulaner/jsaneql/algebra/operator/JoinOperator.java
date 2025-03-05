package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.schema.Value;

import java.util.Map;

public class JoinOperator implements Operator {

    public enum Type {

        INNER,
        LEFT_OUTER,
        RIGHT_OUTER,
        FULL_OUTER,
        LEFT_SEMI,
        RIGHT_SEMI,
        LEFT_ANTI,
        RIGHT_ANTI,
    }

    private final Type type;

    private final Operator left;

    private final Operator right;

    private final Expression condition;

    public JoinOperator(Type type, Operator left, Operator right, Expression condition) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.condition = condition;
    }

    @Override
    public Map<IU, Value> next() {
        return null;
    }
}
