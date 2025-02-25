package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.ArrayList;
import java.util.List;

public class TPCHDatabase implements Database {

    private final List<Table> tables;

    public TPCHDatabase() {
        tables = new ArrayList<>();
        tables.add(new TPCHTable("lineitem", new TPCHTableColumn(ValueType.INTEGER, "id")));
        tables.add(new TPCHTable("customer", new TPCHTableColumn(ValueType.INTEGER, "id")));
    }

    @Override
    public Table getTable(String name) {
        return tables.stream()
                .filter(table -> table.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Table> getTables() {
        return tables;
    }
}
