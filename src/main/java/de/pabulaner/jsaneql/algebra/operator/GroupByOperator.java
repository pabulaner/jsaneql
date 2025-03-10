package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.compile.SQLWriter;
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

    public GroupByOperator(Operator input, List<MapOperator.Entry> groupBy, List<Aggregation> aggregates) {
        this.input = input;
        this.groupBy = groupBy;
        this.aggregates = aggregates;
    }

    @Override
    public void generate(SQLWriter out) {
        out.write("(SELECT ");

        boolean first = true;
        for (MapOperator.Entry groupBy : groupBy) {
            if (first) {
                first = false;
            } else {
                out.write(", ");
            }

            groupBy.getValue().generate(out);
            out.write(" AS ");
            out.writeIU(groupBy.getIU());
        }

        for (Aggregation aggregation : aggregates) {
            if (first) {
                first = false;
            } else {
                out.write(", ");
            }

            switch (aggregation.getOperation()) {
                case COUNT_STAR: out.write("COUNT(*)"); break;
                case COUNT: out.write("COUNT("); break;
                case COUNT_DISTINCT: out.write("COUNT(DISTINCT "); break;
                case SUM: out.write("SUM("); break;
                case SUM_DISTINCT: out.write("SUM(DISTINCT "); break;
                case MIN: out.write("MIN("); break;
                case MAX: out.write("MAX("); break;
                case AVG: out.write("AVG("); break;
                case AVG_DISTINCT: out.write("AVG(DISTINCT "); break;
            }

            if (aggregation.getOperation() != Operation.COUNT_STAR) {
                aggregation.getValue().generate(out);
                out.write(")");
            }

            out.write(" AS ");
            out.writeIU(aggregation.getIU());
        }

        out.write(" FROM ");
        input.generate(out);
        out.write("s GROUP BY ");

        if (groupBy.isEmpty()) {
            out.write("true");
        } else {
            for (int i = 0; i < groupBy.size(); i++) {
                if (i != 0) {
                    out.write(", ");
                }

                out.write(String.valueOf(i + 1));
            }
        }

        out.write(")");
    }
}
