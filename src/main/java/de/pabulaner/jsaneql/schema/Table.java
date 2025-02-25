package de.pabulaner.jsaneql.schema;

import java.util.Iterator;
import java.util.List;

public interface Table {

    String getName();

    TableColumn getColumn(String name);

    List<TableColumn> getColumns();

    Iterator<TableRow> getRows();
}
