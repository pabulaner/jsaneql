package de.pabulaner.jsaneql.semana.scope;

import java.util.ArrayList;
import java.util.List;

public class Binding {

    private final String name;

    private final List<Entry> entries;

    private boolean ambiguous;

    public Binding(String name, boolean ambiguous) {
        this.name = name;
        this.entries = new ArrayList<>();
        this.ambiguous = ambiguous;
    }

    public String getName() {
        return name;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public boolean isAmbiguous() {
        return ambiguous;
    }

    public void setAmbiguous(boolean ambiguous) {
        this.ambiguous = ambiguous;
    }
}
