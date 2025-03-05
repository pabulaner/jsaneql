package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.schema.TableRow;
import de.pabulaner.jsaneql.schema.Value;

import java.util.Map;

public class FilterOperator implements Operator {

    private final Operator input;

    private final Expression condition;

    public FilterOperator(Operator input, Expression condition) {
        this.input = input;
        this.condition = condition;
    }

    @Override
    public Map<IU, Value> next() {
        Map<IU, Value> row;

        while ((row = input.next()) != null) {
            if (condition.getValue(row).getBoolean()) {
                return row;
            }
        }

        return null;
    }
}
