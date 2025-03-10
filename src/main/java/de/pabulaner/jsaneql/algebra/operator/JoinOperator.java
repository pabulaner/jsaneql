package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.compile.SQLWriter;

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
    public void generate(SQLWriter out) {
        out.write("(SELECT * FROM ");

        switch (type) {
            case INNER:
                left.generate(out);
                out.write(" l INNER JOIN ");
                right.generate(out);
                out.write(" r ON ");
                condition.generate(out);
                break;
            case LEFT_OUTER:
                left.generate(out);
                out.write(" l LEFT OUTER JOIN ");
                right.generate(out);
                out.write(" r ON ");
                condition.generate(out);
                break;
            case RIGHT_OUTER:
                left.generate(out);
                out.write(" l RIGHT OUTER JOIN ");
                right.generate(out);
                out.write(" r ON ");
                condition.generate(out);
                break;
            case FULL_OUTER:
                left.generate(out);
                out.write(" l FULL OUTER JOIN ");
                right.generate(out);
                out.write(" r ON ");
                condition.generate(out);
                break;
            case LEFT_SEMI:
                left.generate(out);
                out.write(" l WHERE EXISTS(SELECT * FROM ");
                right.generate(out);
                out.write(" r WHERE ");
                condition.generate(out);
                out.write(")");
                break;
            case RIGHT_SEMI:
                right.generate(out);
                out.write(" r WHERE EXISTS(SELECT * FROM ");
                left.generate(out);
                out.write(" l WHERE ");
                condition.generate(out);
                out.write(")");
                break;
            case LEFT_ANTI:
                left.generate(out);
                out.write(" l WHERE NOT EXISTS(SELECT * FROM ");
                right.generate(out);
                out.write(" r WHERE ");
                condition.generate(out);
                out.write(")");
                break;
            case RIGHT_ANTI:
                right.generate(out);
                out.write(" r WHERE NOT EXISTS(SELECT * FROM ");
                left.generate(out);
                out.write(" l WHERE ");
                condition.generate(out);
                out.write(")");
                break;
        }

        out.write(")");
    }
}
