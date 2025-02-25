package de.pabulaner.jsaneql.parser.ast;

public class ArgNode extends Node {

    /**
     * Is it a flat or list argument?
     */
    private final boolean list;

    /**
     * The name of the argument, if any.
     */
    private final Node name;

    /**
     * The value of the argument.
     */
    private final Node value;

    public ArgNode(boolean list, Node name, Node value) {
        super(Node.Type.ARG);

        this.list = list;
        this.name = name;
        this.value = value;
    }

    public boolean isList() {
        return list;
    }

    public Node getName() {
        return name;
    }

    public Node getValue() {
        return value;
    }
}
