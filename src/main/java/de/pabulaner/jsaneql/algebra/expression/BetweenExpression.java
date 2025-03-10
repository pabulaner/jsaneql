package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.ValueType;

public class BetweenExpression implements Expression {

    private final Expression base;

    private final Expression lower;

    private final Expression upper;

    public BetweenExpression(Expression base, Expression lower, Expression upper) {
        this.base = base;
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public void generate(SQLWriter out) {
        base.generate(out);
        out.write(" BETWEEN ");
        lower.generate(out);
        out.write(" AND ");
        upper.generate(out);
    }

    @Override
    public ValueType getType() {
        return ValueType.BOOLEAN;
    }
}
