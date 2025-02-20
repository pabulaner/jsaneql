package de.pabulaner.jsaneql.parser.ast;

public class AccessNode extends Node {

    private final Node base;

    private final Node part;

    public AccessNode(Node base, Node part) {
        super(Type.ACCESS, null);

        this.base = base;
        this.part = part;
    }
}
