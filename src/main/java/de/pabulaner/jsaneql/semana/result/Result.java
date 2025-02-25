package de.pabulaner.jsaneql.semana.result;

import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.algebra.operator.Operator;
import de.pabulaner.jsaneql.semana.scope.Scope;

public class Result {

    private final ScalarInfo scalar;

    private final TableInfo table;

    public Result(Expression expression) {
        this(expression, new OrderingInfo(false));
    }

    public Result(Expression expression, OrderingInfo orderingInfo) {
        this(new ScalarInfo(expression, orderingInfo), null);
    }

    public Result(Operator operator, Scope scope) {
        this(null, new TableInfo(operator, scope));
    }

    private Result(ScalarInfo scalar, TableInfo table) {
        this.scalar = scalar;
        this.table = table;
    }

    public boolean isScalar() {
        return scalar != null;
    }

    public boolean isTable() {
        return table != null;
    }

    public Expression scalar() {
        return scalar.getExpression();
    }

    public OrderingInfo ordering() {
        return scalar.getOrdering();
    }

    public Operator table() {
        return table.getOperator();
    }

    public Scope scope() {
        return table.getScope();
    }
}
