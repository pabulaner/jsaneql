package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.TableColumn;
import de.pabulaner.jsaneql.schema.ValueType;

public class TPCHTableColumn implements TableColumn {

    private final ValueType type;

    private final String name;

    public TPCHTableColumn(String name, ValueType type) {
        this.type = type;
        this.name = name;
    }

    @Override
    public ValueType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
