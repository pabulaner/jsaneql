package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.Column;
import de.pabulaner.jsaneql.compile.SQLWriter;

import java.util.List;

public class ScanOperator implements Operator {

    private final String name;

    private final List<Column> columns;

    public ScanOperator(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }


    @Override
    public void generate(SQLWriter out) {
        out.write("(SELECT ");

        boolean first = true;
        for (Column column : columns) {
            if (first) {
                first = false;
            } else {
                out.write(", ");
            }

            out.write(column.getName() + " AS ");
            out.writeIU(column.getIU());
        }

        out.write(" FROM " + name + ")");
    }
}
