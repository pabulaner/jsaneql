package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.schema.Value;

import java.util.List;
import java.util.Map;

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
    }

    private final Operator input;

    private final List<MapOperator.Entry> groupBy;

    private final List<Aggregation> aggregates;

    public GroupByOperator(Operator input, List<MapOperator.Entry> groupBy, List<Aggregation> aggregates) {
        this.input = input;
        this.groupBy = groupBy;
        this.aggregates = aggregates;
    }

    @Override
    public Map<IU, Value> next() {
        return null;
    }
}
