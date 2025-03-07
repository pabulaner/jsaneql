package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.schema.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class JoinOperator implements Operator {

    public enum Type {

        INNER,
        LEFT_OUTER,
        RIGHT_OUTER,
        FULL_OUTER,
        LEFT_SEMI,
        RIGHT_SEMI,
        LEFT_ANTI,
        RIGHT_ANTI,
    }

    private final Type type;

    private final Operator left;

    private final Operator right;

    private final Expression condition;

    private Queue<Map<IU, Value>> result;

    public JoinOperator(Type type, Operator left, Operator right, Expression condition) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.condition = condition;
        this.result = null;
    }

    @Override
    public Map<IU, Value> next() {
        if (result == null) {
            result = new LinkedList<>();

            List<Map<IU, Value>> leftRows = new ArrayList<>();
            Map<IU, Value> row;

            while ((row = left.next()) != null) {
                leftRows.add(row);
            }

            while ((row = right.next()) != null) {
                for (Map<IU, Value> other : leftRows) {
                    Map<IU, Value> combined = new HashMap<>();

                    combined.putAll(row);
                    combined.putAll(other);

                    Map<IU, Value> joined = join(row, other, condition.getValue(combined).getBoolean());

                    if (joined != null) {
                        result.add(joined);
                    }
                }
            }
        }

        return result.poll();
    }

    private Map<IU, Value> join(Map<IU, Value> leftRow, Map<IU, Value> rightRow, boolean condition) {
        Map<IU, Value> row = new HashMap<>();
        Map<IU, Value> leftNull = new HashMap<>(leftRow);
        Map<IU, Value> rightNull = new HashMap<>(rightRow);

        leftNull.replaceAll((key, value) -> null);
        rightNull.replaceAll((key, value) -> null);

        if (condition) {
            switch (type) {
                case INNER: row.putAll(leftRow); row.putAll(rightRow); break;
                case LEFT_OUTER: row.putAll(leftRow);
                case RIGHT_OUTER:
                    break;
                case FULL_OUTER:
                    break;
                case LEFT_SEMI:
                    break;
                case RIGHT_SEMI:
                    break;
                case LEFT_ANTI:
                    break;
                case RIGHT_ANTI:
                    break;
            }
        }

        return null;
    }
}
