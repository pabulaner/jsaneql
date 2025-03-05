package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.TableRow;
import de.pabulaner.jsaneql.schema.Value;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScanOperator implements Operator {

    private final Iterator<TableRow> rows;

    private final List<IU> columns;

    public ScanOperator(Table table, List<IU> columns) {
        this.rows = table.getRows();
        this.columns = columns;
    }

    public Map<IU, Value> next() {
        if (!rows.hasNext()) {
            return null;
        }

        Map<IU, Value> result = new HashMap<>();
        TableRow row = rows.next();

        for (int i = 0; i < columns.size(); i++) {
            result.put(columns.get(i), row.getValue(i));
        }

        return result;
    }
}
