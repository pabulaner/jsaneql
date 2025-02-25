package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.TableColumn;
import de.pabulaner.jsaneql.schema.TableRow;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TPCHTable implements Table {

    private final String name;

    private final List<TableColumn> columns;

    public TPCHTable(String name, TableColumn... columns) {
        this.name = name;
        this.columns = Arrays.asList(columns);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TableColumn getColumn(String name) {
        return columns.stream()
                .filter(column -> column.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TableColumn> getColumns() {
        return columns;
    }

    @Override
    public Iterator<TableRow> getRows() {
        return null;
    }
}
