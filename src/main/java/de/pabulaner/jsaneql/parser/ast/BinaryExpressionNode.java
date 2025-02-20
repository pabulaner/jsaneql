package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.tokenizer.Token;

public class BinaryExpressionNode extends Node {

    /**
     * The left side of the binary expression.
     */
    private final Node left;

    /**
     * The right side of the binary expression.
     */
    private final Node right;

    public BinaryExpressionNode(Token token, Node left, Node right) {
        super(Type.BINARY_EXPRESSION, token);

        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
