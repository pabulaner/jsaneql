package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.schema.Value;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GroupByOperator implements Operator {

    public enum Operation {

        COUNT_STAR,
        COUNT,
        COUNT_DISTINCT,
        SUM,
        SUM_DISTINCT,
        MIN,
        MAX,
        AVG,
        AVG_DISTINCT,
    }

    public static class Aggregation {

        private final IU iu;

        private final Expression value;

        private final Operation operation;

        public Aggregation(IU iu, Expression value, Operation operation) {
            this.iu = iu;
            this.value = value;
            this.operation = operation;
        }

        public IU getIU() {
            return iu;
        }

        public Expression getValue() {
            return value;
        }

        public Operation getOperation() {
            return operation;
        }
    }

    private final Operator input;

    private final List<MapOperator.Entry> groupBy;

    private final List<Aggregation> aggregates;

    private Map<Value[], Value[]> result;

    public GroupByOperator(Operator input, List<MapOperator.Entry> groupBy, List<Aggregation> aggregates) {
        this.input = input;
        this.groupBy = groupBy;
        this.aggregates = aggregates;
        this.result = null;

        for (int i = 0; i < aggregates.size(); i++) {
            Aggregation aggregation = aggregates.get(i);

            switch (aggregation.getOperation()) {
                case AVG: aggregates.add(i + 1, new Aggregation(null, null, Operation.COUNT)); break;
                case AVG_DISTINCT: aggregates.add(i + 1, new Aggregation(null, null, Operation.COUNT_DISTINCT)); break;
            }
        }
    }

    @Override
    public Map<IU, Value> next() {
        if (result == null) {
            result = new HashMap<>();
            Map<IU, Value> row;

            while ((row = input.next()) != null) {
                Value[] key = new Value[groupBy.size()];
                Value[] value = new Value[aggregates.size()];

                for (int i = 0; i < groupBy.size(); i++) {
                    key[i] = groupBy.get(i).getValue().getValue(row);
                }

                for (int i = 0; i < aggregates.size(); i++) {
                    Aggregation aggregation = aggregates.get(i);

                    switch (aggregation.getOperation()) {
                        case COUNT_STAR: value[i] = Value.ofInteger(1L); break;
                        case COUNT: break;
                        case COUNT_DISTINCT: break;
                        case SUM: value[i] = aggregation.getValue().getValue(row); break;
                        case SUM_DISTINCT: break;
                        case MIN: break;
                        case MAX:
                            break;
                        case AVG:
                            break;
                        case AVG_DISTINCT:
                            break;
                    }
                }
            }
        }

        return null;
    }
}
