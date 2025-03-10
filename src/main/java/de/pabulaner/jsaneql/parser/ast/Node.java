package de.pabulaner.jsaneql.parser.ast;

public abstract class Node {

    /**
     * The types a node can have.
     */
    public enum Type {

        QUERY_BODY,
        TOKEN,
        ACCESS,
        CALL,
        ARG,
        LIST,
        UNARY_EXPRESSION,
        BINARY_EXPRESSION,
        CAST,
    }

    /**
     * The type of the node.
     */
    private final Type type;

    /**
     * The constructor.
     */
    public Node(Type type) {
        this.type = type;
    }

    /**
     * Gets the type.
     */
    public Type getType() {
        return type;
    }
}
