package de.pabulaner.jsaneql.parser.ast;

public class QueryBodyNode extends Node {

    /**
     * The body of the query that is executed.
     */
    private final Node body;

    public QueryBodyNode(Node body) {
        super(Type.QUERY_BODY, null);
        this.body = body;
    }

    public Node getBody() {
        return body;
    }
}
