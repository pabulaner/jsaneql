package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.compile.SQLWriter;

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

    @Override
    public void generate(SQLWriter out) {
        out.write("(SELECT * FROM ");
        input.generate(out);
        out.write(" s");

        if (!order.isEmpty()) {
            out.write(" ORDER BY ");

            boolean first = true;
            for (Entry order : order) {
                if (first) {
                    first = false;
                } else {
                    out.write(", ");
                }

                order.value.generate(out);

                if (order.descending) {
                    out.write(" DESC");
                }
            }
        }

        out.write(" LIMIT " + limit + " OFFSET " + offset + ")");
    }
}
