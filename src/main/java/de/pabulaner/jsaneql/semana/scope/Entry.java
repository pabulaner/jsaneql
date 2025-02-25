package de.pabulaner.jsaneql.semana.scope;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.ValueType;

public class Entry {

    private final IU iu;

    private final ValueType type;

    private final String name;

    private boolean ambiguous;

    public Entry(ValueType type, String name, boolean ambiguous) {
        this.iu = new IU();
        this.type = type;
        this.name = name;
        this.ambiguous = ambiguous;
    }

    public IU getIU() {
        return iu;
    }

    public ValueType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isAmbiguous() {
        return ambiguous;
    }

    public void setAmbiguous(boolean ambiguous) {
        this.ambiguous = ambiguous;
    }
}
