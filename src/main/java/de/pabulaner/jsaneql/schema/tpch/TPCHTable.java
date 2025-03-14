package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.Column;

import java.util.Arrays;
import java.util.List;

public class TPCHTable implements Table {

    private final List<Column> columns;

    public TPCHTable(Column... columns) {
        this.columns = Arrays.asList(columns);
    }

    @Override
    public List<Column> getColumns() {
        return columns;
    }
}
