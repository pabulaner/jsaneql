package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.tokenizer.Token;

public class UnaryExpression extends Node {

    /**
     * The value for the unary expression.
     */
    private final Node value;

    public UnaryExpression(Token token, Node value) {
        super(Type.UNARY_EXPRESSION, token);
        this.value = value;
    }

    public Node getValue() {
        return value;
    }
}
