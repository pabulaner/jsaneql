package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IUColumn;
import de.pabulaner.jsaneql.compile.SQLWriter;

import java.util.List;

public class ScanOperator implements Operator {

    private final String name;

    private final List<IUColumn> columns;

    public ScanOperator(String name, List<IUColumn> columns) {
        this.name = name;
        this.columns = columns;
    }


    @Override
    public void generate(SQLWriter out) {
        out.write("(SELECT ");

        boolean first = true;
        for (IUColumn column : columns) {
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
