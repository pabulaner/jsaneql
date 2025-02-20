package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.tokenizer.Token;

public class Node {

    public enum Type {

        QUERY_BODY,
        TOKEN,
        ACCESS,
        FUNC_CALL,
        FUNC_ARG,
        FUNC_NAMED_ARG,
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
     * The token that represents the node.
     */
    private final Token token;

    public Node(Type type, Token token) {
        this.type = type;
        this.token = token;
    }

    public Type getType() {
        return type;
    }

    public Token getToken() {
        return token;
    }
}
