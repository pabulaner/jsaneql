package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.schema.Value;

import java.util.List;
import java.util.Map;

public class MapOperator implements Operator {

    public static class Entry {

        private final IU iu;

        private final Expression value;

        public Entry(IU iu, Expression value) {
            this.iu = iu;
            this.value = value;
        }

        public IU getIU() {
            return iu;
        }

        public Expression getValue() {
            return value;
        }
    }

    private final Operator input;

    private final List<Entry> computations;

    public MapOperator(Operator input, List<Entry> computations) {
        this.input = input;
        this.computations = computations;
    }

    @Override
    public Map<IU, Value> next() {
        Map<IU, Value> row = input.next();

        if (row != null) {
            for (Entry computation : computations) {
                row.put(computation.getIU(), computation.getValue().getValue(row));
            }
        }

        return row;
    }
}
