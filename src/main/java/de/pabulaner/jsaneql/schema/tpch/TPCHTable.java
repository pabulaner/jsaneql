package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.TableColumn;

import java.util.Arrays;
import java.util.List;

public class TPCHTable implements Table {

    private final List<TableColumn> columns;

    public TPCHTable(TableColumn... columns) {
        this.columns = Arrays.asList(columns);
    }

    @Override
    public List<TableColumn> getColumns() {
        return columns;
    }
}
