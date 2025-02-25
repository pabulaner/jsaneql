package de.pabulaner.jsaneql.parser.ast;

public class QueryNode extends Node {

    /**
     * The let statements of the query.
     */
    private final Node lets;

    /**
     * The body of the query that is executed.
     */
    private final Node body;

    public QueryNode(Node body) {
        super(Type.QUERY_BODY);

        this.lets = null;
        this.body = body;
    }

    public Node getBody() {
        return body;
    }
}
