package de.pabulaner.jsaneql.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TestTable implements Table {

    private final String name;

    private final List<TableColumn> columns;

    private final List<TableRow> rows;

    public TestTable(String name, List<TableColumn> columns) {
        this.name = name;
        this.columns = columns;
        this.rows = new ArrayList<>();
    }

    public TestTable addRow(String... values) {
        List<Value> result = new ArrayList<>();

        for (int i = 0; i < columns.size(); i++) {
            result.add(ValueCast.cast(Value.ofString(values[i]), columns.get(i).getType()));
        }

        rows.add(new TestTableRow(result));
        return this;
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
        return rows.iterator();
    }
}
