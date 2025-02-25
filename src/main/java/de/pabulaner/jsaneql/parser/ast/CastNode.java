package de.pabulaner.jsaneql.parser.ast;

public class CastNode extends Node {

    /**
     * The value to cast.
     */
    private final Node value;

    /**
     * The type to cast the value to.
     */
    private final Node cast;

    public CastNode(Node value, Node cast) {
        super(Type.CAST);

        this.value = value;
        this.cast = cast;
    }

    public Node getValue() {
        return value;
    }

    public Node getCast() {
        return cast;
    }
}
