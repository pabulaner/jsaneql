package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.expression.Expression;

import java.util.List;

public class OrderByOperator implements Operator {

    public static class Entry {

        private final Expression value;

        private final boolean descending;

        public Entry(Expression value, boolean descending) {
            this.value = value;
            this.descending = descending;
        }
    }

    private final Operator input;

    private final List<Entry> order;

    private final long limit;

    private final long offset;

    public OrderByOperator(Operator input, List<Entry> order, long limit, long offset) {
        this.input = input;
        this.order = order;
        this.limit = limit;
        this.offset = offset;
    }
}
