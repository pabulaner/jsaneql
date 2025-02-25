package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.schema.Table;

public class ScanOperator implements Operator {

    private final Table table;

    public ScanOperator(Table table) {
        this.table = table;
    }
}
