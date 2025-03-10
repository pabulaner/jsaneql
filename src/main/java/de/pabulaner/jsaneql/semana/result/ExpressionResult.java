package de.pabulaner.jsaneql.semana.result;

import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.algebra.operator.Operator;
import de.pabulaner.jsaneql.semana.binding.Binding;

public class ExpressionResult {

    private final ScalarInfo scalar;

    private final TableInfo table;

    public ExpressionResult(Expression expression) {
        this(expression, new OrderingInfo(false));
    }

    public ExpressionResult(Expression expression, OrderingInfo orderingInfo) {
        this(new ScalarInfo(expression, orderingInfo), null);
    }

    public ExpressionResult(Operator operator, Binding binding) {
        this(null, new TableInfo(operator, binding));
    }

    private ExpressionResult(ScalarInfo scalar, TableInfo table) {
        this.scalar = scalar;
        this.table = table;
    }

    public boolean isScalar() {
        return scalar != null;
    }

    public boolean isTable() {
        return table != null;
    }

    @SuppressWarnings("unchecked")
    public <TExpression extends Expression> TExpression scalar() {
        return (TExpression) scalar.getExpression();
    }

    public OrderingInfo ordering() {
        return scalar.getOrdering();
    }

    @SuppressWarnings("unchecked")
    public <TOperator extends Operator> TOperator table() {
        return (TOperator) table.getOperator();
    }

    public Binding binding() {
        return table.getBinding();
    }
}
