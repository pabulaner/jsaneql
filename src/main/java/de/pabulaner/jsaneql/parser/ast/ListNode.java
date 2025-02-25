package de.pabulaner.jsaneql.parser.ast;

import java.util.List;

public class ListNode extends Node {

    /**
     * The values inside the list.
     */
    private final List<Node> values;

    public ListNode(List<Node> values) {
        super(Type.LIST);
        this.values = values;
    }

    public List<Node> getValues() {
        return values;
    }
}
