package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.schema.Value;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

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

    private Queue<Map<IU, Value>> result;

    public OrderByOperator(Operator input, List<Entry> order, long limit, long offset) {
        this.input = input;
        this.order = order;
        this.limit = limit;
        this.offset = offset;
        this.result = null;
    }

    @Override
    public Map<IU, Value> next() {
        if (result == null) {
            result = new LinkedList<>();
            Map<IU, Value> row;

            while ((row = input.next()) != null) {
                result.add(row);
            }

            result = result.stream()
                    .sorted(this::compare)
                    .skip(offset)
                    .limit(limit)
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        return result.poll();
    }

    private int compare(Map<IU, Value> first, Map<IU, Value> second) {
        for (Entry entry : order) {
            Value firstValue = entry.value.getValue(first);
            Value secondValue = entry.value.getValue(second);

            long value = firstValue.compare(secondValue);

            if (value < 0) return entry.descending ? 1 : -1;
            if (value > 0) return entry.descending ? -1 : 1;
        }

        return 0;
    }
}
