package de.pabulaner.jsaneql.semana.result;

import de.pabulaner.jsaneql.algebra.expression.Expression;

public class ScalarInfo {

    private final Expression expression;

    private final OrderingInfo ordering;

    public ScalarInfo(Expression expression, OrderingInfo ordering) {
        this.expression = expression;
        this.ordering = ordering;
    }

    public Expression getExpression() {
        return expression;
    }

    public OrderingInfo getOrdering() {
        return ordering;
    }
}
