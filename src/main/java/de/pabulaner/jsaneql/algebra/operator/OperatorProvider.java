package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.Column;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.algebra.operator.GroupByOperator;
import de.pabulaner.jsaneql.algebra.operator.JoinOperator;
import de.pabulaner.jsaneql.algebra.operator.MapOperator;
import de.pabulaner.jsaneql.algebra.operator.Operator;
import de.pabulaner.jsaneql.algebra.operator.OrderByOperator;

import java.util.List;

public interface OperatorProvider<TOperator extends Operator, TExpression extends Expression> {

    TOperator scan(String name, List<Column> columns);

    TOperator filter(TOperator input, TExpression condition);

    TOperator map(TOperator input, List<MapOperator.Entry> computations);

    TOperator join(TOperator left, TOperator right, TExpression condition, JoinOperator.Type type);

    TOperator groupBy(TOperator input, List<MapOperator.Entry> groupBy, List<GroupByOperator.Aggregation> aggregates);

    TOperator orderBy(TOperator input, List<OrderByOperator.Entry> order, long limit, long offset);
}
