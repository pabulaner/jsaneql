package de.pabulaner.jsaneql.semana.result;

import de.pabulaner.jsaneql.algebra.operator.Operator;
import de.pabulaner.jsaneql.semana.scope.Scope;

public class TableInfo {

    private final Operator operator;

    private final Scope scope;

    public TableInfo(Operator operator, Scope scope) {
        this.operator = operator;
        this.scope = scope;
    }

    public Operator getOperator() {
        return operator;
    }

    public Scope getScope() {
        return scope;
    }
}
