package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.schema.TableRow;

public class FilterOperator implements Operator {

    private final Operator input;

    private final Expression condition;

    public FilterOperator(Operator input, Expression condition) {
        this.input = input;
        this.condition = condition;
    }
}
