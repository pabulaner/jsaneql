package de.pabulaner.jsaneql.schema;

import java.util.List;

public interface Database {

    Table getTable(String name);

    List<Table> getTables();
}
