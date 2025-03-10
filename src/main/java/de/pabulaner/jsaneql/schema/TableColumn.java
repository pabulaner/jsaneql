package de.pabulaner.jsaneql.schema;

public class TableColumn {

    private final String name;

    private final ValueType type;

    public TableColumn(String name, ValueType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ValueType getType() {
        return type;
    }
}
