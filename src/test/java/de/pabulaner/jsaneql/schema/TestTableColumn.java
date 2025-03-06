package de.pabulaner.jsaneql.schema;

public class TestTableColumn implements TableColumn {

    private final String name;

    private final ValueType type;

    public TestTableColumn(String name, ValueType type) {
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
