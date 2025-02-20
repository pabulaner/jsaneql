package de.pabulaner.jsaneql.ast.expression;

public abstract class Expression {

    public enum Type {

        BOOLEAN,
        NUMBER,
        STRING,
    }

    private final Type type;

    public Expression(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
