package de.pabulaner.jsaneql.semana.binding;

import de.pabulaner.jsaneql.algebra.IU;

import java.util.HashMap;
import java.util.Map;

public class Scope {

    private final Map<String, IU> columns;

    private boolean ambiguous;

    public Scope() {
        columns = new HashMap<>();
        ambiguous = false;
    }

    public Scope(Scope scope) {
        columns = new HashMap<>(scope.columns);
        ambiguous = scope.ambiguous;
    }

    public Map<String, IU> getColumns() {
        return columns;
    }

    public boolean isAmbiguous() {
        return ambiguous;
    }

    public void setAmbiguous(boolean ambiguous) {
        this.ambiguous = ambiguous;
    }
}
