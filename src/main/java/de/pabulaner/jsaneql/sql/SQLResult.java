package de.pabulaner.jsaneql.sql;

import de.pabulaner.jsaneql.schema.TableColumn;
import de.pabulaner.jsaneql.schema.Value;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class SQLResult implements Iterator<Value[]> {

    private final List<TableColumn> columns;

    private final Queue<Value[]> rows;

    public SQLResult(List<TableColumn> columns, Queue<Value[]> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    @Override
    public boolean hasNext() {
        return !rows.isEmpty();
    }

    @Override
    public Value[] next() {
        return rows.poll();
    }

    public List<TableColumn> getColumns() {
        return columns;
    }
}
