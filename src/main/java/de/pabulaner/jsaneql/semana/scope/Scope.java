package de.pabulaner.jsaneql.semana.scope;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Scope {

    /**
     * The name of the binding for columns that have no binding.
     */
    private static final String NO_BINDING = "";

    /**
     * The tables with columns for the scope.
     */
    private final List<Binding> bindings;

    public Scope() {
        bindings = new ArrayList<>();
        create();
    }

    private void create() {
        bindings.clear();
        bindings.add(new Binding(NO_BINDING, false));
    }

    public Binding addBinding(String name) {
        boolean[] ambiguous = { false };

        bindings.stream()
                .filter(binding -> binding.getName().equals(name))
                .forEach(binding -> {
                    ambiguous[0] = true;
                    binding.setAmbiguous(true);
                });

        Binding result = new Binding(name, ambiguous[0]);

        bindings.add(result);
        return result;
    }

    public Entry addEntry(ValueType type, String name) {
        return addEntry(getNoBinding(), type, name);
    }

    public Entry addEntry(Binding binding, ValueType type, String name) {
        boolean[] ambiguous = { false };

        bindings.stream()
                .flatMap(other -> other.getEntries().stream())
                .filter(column -> column.getName().equals(name))
                .forEach(other -> {
                    ambiguous[0] = true;
                    other.setAmbiguous(true);
                });

        Entry entry = new Entry(type, name, ambiguous[0]);

        binding.getEntries().add(entry);
        return entry;
    }

    public Binding lookupBinding(String bindingName) {
        return bindings.stream()
                .filter(binding -> binding.getName().equals(bindingName))
                .findFirst()
                .orElse(null);
    }

    public Entry lookupEntry(String entryName) {
        return lookupEntry(NO_BINDING, entryName);
    }

    public Entry lookupEntry(String bindingName, String entryName) {
        return bindings.stream()
                .filter(binding -> bindingName.isEmpty() || bindingName.equals(binding.getName()))
                .flatMap(binding -> binding.getEntries().stream())
                .filter(entry -> entry.getName().equals(entryName))
                .findFirst()
                .orElse(null);
    }

    public void rename(String name) {
        List<Entry> entries = bindings.stream()
                .flatMap(binding -> binding.getEntries().stream())
                .collect(Collectors.toList());

        create();

        Binding binding = addBinding(name);
        entries.forEach(entry -> addEntry(binding, entry.getType(), entry.getName()));
    }

    public Scope join(Scope other) {
        Scope result = new Scope();

        Consumer<Scope> add = scope -> scope.bindings.forEach(binding -> {
            Binding added = result.addBinding(binding.getName());

            binding.getEntries().forEach(entry ->
                    result.addEntry(added, entry.getType(), entry.getName()));
        });

        add.accept(this);
        add.accept(other);

        return result;
    }

    private Binding getNoBinding() {
        return bindings.stream()
                .filter(binding -> binding.getName().equals(NO_BINDING))
                .findFirst()
                .orElse(null);
    }
}
