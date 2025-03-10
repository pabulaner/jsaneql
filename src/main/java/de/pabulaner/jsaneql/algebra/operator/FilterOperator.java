package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.compile.SQLWriter;

public class FilterOperator implements Operator {

    private final Operator input;

    private final Expression condition;

    public FilterOperator(Operator input, Expression condition) {
        this.input = input;
        this.condition = condition;
    }

    @Override
    public void generate(SQLWriter out) {
        out.write("(SELECT * FROM ");
        input.generate(out);
        out.write(" s WHERE ");
        condition.generate(out);
        out.write(")");
    }
}
