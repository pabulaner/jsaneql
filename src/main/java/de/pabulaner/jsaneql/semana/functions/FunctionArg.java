package de.pabulaner.jsaneql.semana.functions;

public class FunctionArg {

    public enum Type {

        SCALAR,
        TABLE,
        EXPRESSION,
        EXPRESSION_LIST,
        SYMBOL,
        SYMBOL_LIST,
    }

    private final String name;

    private final Type type;

    private final boolean optional;

    public FunctionArg(String name, Type type) {
        this(name, type, false);
    }

    public FunctionArg(String name, Type type, boolean optional) {
        this.name = name;
        this.type = type;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }
}
