package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.algebra.expression.UnaryExpression;

public class UnaryNode extends Node {

    /**
     * The operation for the unary expression.
     */
    private final UnaryExpression.Operation operation;

    /**
     * The value for the unary expression.
     */
    private final Node value;

    public UnaryNode(UnaryExpression.Operation operation, Node value) {
        super(Type.UNARY_EXPRESSION);

        this.operation = operation;
        this.value = value;
    }

    public UnaryExpression.Operation getOperation() {
        return operation;
    }

    public Node getValue() {
        return value;
    }
}
