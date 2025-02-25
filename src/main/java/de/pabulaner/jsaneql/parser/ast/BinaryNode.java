package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.algebra.expression.BinaryExpression;

public class BinaryNode extends Node {

    /**
     * The operator of the binary expression.
     */
    private final BinaryExpression.Operation operation;

    /**
     * The left side of the binary expression.
     */
    private final Node left;

    /**
     * The right side of the binary expression.
     */
    private final Node right;

    public BinaryNode(BinaryExpression.Operation operation, Node left, Node right) {
        super(Type.BINARY_EXPRESSION);

        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    public BinaryExpression.Operation getOperation() {
        return operation;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
