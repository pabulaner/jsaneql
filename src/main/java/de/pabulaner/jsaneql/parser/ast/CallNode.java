package de.pabulaner.jsaneql.parser.ast;

import java.util.List;

public class CallNode extends Node {

    /**
     * The target of the function.
     */
    private final Node func;

    /**
     * The function args.
     */
    private final List<Node> args;

    public CallNode(Node func, List<Node> args) {
        super(Type.CALL);

        this.func = func;
        this.args = args;
    }

    public Node getFunc() {
        return func;
    }

    public List<Node> getArgs() {
        return args;
    }
}
