package de.pabulaner.jsaneql.semana.binding;

import de.pabulaner.jsaneql.algebra.IUColumn;
import de.pabulaner.jsaneql.algebra.IU;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Binding {

    public static final IU AMBIGUOUS_IU = new IU(null);

    public static final IU AMBIGUOUS_SCOPE = new IU(null);

    private final List<IUColumn> columns;

    private final Map<String, IU> columnLookup;

    private final Map<String, Scope> scopes;

    private GroupByScope groupByScope;

    public Binding() {
        columns = new ArrayList<>();
        columnLookup = new HashMap<>();
        scopes = new HashMap<>();
        groupByScope = null;
    }

    public Binding(Binding binding) {
        columns = new ArrayList<>();
        columnLookup = new HashMap<>(binding.columnLookup);
        scopes = new HashMap<>();
        groupByScope = binding.groupByScope;

        binding.columns.forEach(column -> columns.add(new IUColumn(column.getName(), column.getIU())));
        binding.scopes.forEach((key, value) -> scopes.put(key, new Scope(value)));
    }

    public Scope addScope(String name) {
        Scope scope = scopes.get(name);

        if (scope != null) {
            scope.getColumns().clear();
            scope.setAmbiguous(true);

            return null;
        }

        scope = new Scope();
        scopes.put(name, scope);

        return scope;
    }

    public void addBinding(String column, IU iu) {
        addBinding(null, column, iu);
    }

    public void addBinding(Scope scope, String column, IU iu) {
        if (scope != null) {
            IU other = scope.getColumns().get(column);
            scope.getColumns().put(column, other != null ? AMBIGUOUS_IU : iu);
        }

        IU other = columnLookup.get(column);

        columnLookup.put(column, other != null ? AMBIGUOUS_IU : iu);
        columns.add(new IUColumn(column, iu));
    }

    public IU lookup(String name) {
        return columnLookup.get(name);
    }

    public IU lookup(String binding, String name) {
        Scope scope = scopes.get(binding);

        if (scope != null) {
            if (scope.isAmbiguous()) {
                return AMBIGUOUS_SCOPE;
            }

            return scope.getColumns().get(name);
        }

        return null;
    }

    public void join(Binding other) {
        columns.addAll(other.columns);

        for (Map.Entry<String, IU> entry : other.columnLookup.entrySet()) {
            if (!columnLookup.containsKey(entry.getKey())) {
                columnLookup.put(entry.getKey(), entry.getValue());
            } else {
                columnLookup.put(entry.getKey(), AMBIGUOUS_IU);
            }
        }

        for (Map.Entry<String, Scope> entry : other.scopes.entrySet()) {
            if (!scopes.containsKey(entry.getKey())) {
                scopes.put(entry.getKey(), entry.getValue());
            } else {
                Scope scope = scopes.get(entry.getKey());

                scope.getColumns().clear();
                scope.setAmbiguous(true);
            }
        }
    }

    public List<IUColumn> getColumns() {
        return columns;
    }

    public Map<String, IU> getColumnLookup() {
        return columnLookup;
    }

    public Map<String, Scope> getScopes() {
        return scopes;
    }

    public GroupByScope getGroupByScope() {
        return groupByScope;
    }

    public void setGroupByScope(GroupByScope groupByScope) {
        this.groupByScope = groupByScope;
    }
}
