package de.pabulaner.jsaneql.schema;

import java.util.List;

public class TestTableRow implements TableRow {

    private final List<Value> values;

    public TestTableRow(List<Value> values) {
        this.values = values;
    }

    @Override
    public Value getValue(int index) {
        return values.get(index);
    }
}
