package de.pabulaner.jsaneql.schema;

public enum ValueType {

    TEXT("text"),
    INTEGER("integer"),
    DECIMAL("decimal"),
    BOOLEAN("boolean"),
    DATE("date"),
    INTERVAL("interval"),
    UNKNOWN("null");

    private final String name;

    ValueType(String name) {
        this.name = name;
    }

    public static ValueType fromName(String name) {
        for (ValueType value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }
}
