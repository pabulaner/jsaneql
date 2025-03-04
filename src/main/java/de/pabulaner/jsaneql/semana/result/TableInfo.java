package de.pabulaner.jsaneql.semana.result;

import de.pabulaner.jsaneql.algebra.operator.Operator;
import de.pabulaner.jsaneql.semana.binding.Binding;

public class TableInfo {

    private final Operator operator;

    private final Binding binding;

    public TableInfo(Operator operator, Binding binding) {
        this.operator = operator;
        this.binding = binding;
    }

    public Operator getOperator() {
        return operator;
    }

    public Binding getBinding() {
        return binding;
    }
}
