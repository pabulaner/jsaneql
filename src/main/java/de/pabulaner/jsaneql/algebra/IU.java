package de.pabulaner.jsaneql.algebra;

import de.pabulaner.jsaneql.schema.ValueType;

public class IU {

    private final ValueType type;

    public IU(ValueType type) {
        this.type = type;
    }

    public ValueType getType() {
        return type;
    }
}
