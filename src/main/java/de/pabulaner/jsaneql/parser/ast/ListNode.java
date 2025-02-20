package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.tokenizer.Token;

import java.util.List;

public class ListNode extends Node {

    /**
     * The values inside the list.
     */
    private final List<Node> values;

    public ListNode(Token token, List<Node> values) {
        super(Type.LIST, token);
        this.values = values;
    }

    public List<Node> getValues() {
        return values;
    }
}
