package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.TableColumn;
import de.pabulaner.jsaneql.schema.ValueType;

public class TPCHTableColumn implements TableColumn {

    private final String name;

    private final ValueType type;

    public TPCHTableColumn(String name, ValueType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
