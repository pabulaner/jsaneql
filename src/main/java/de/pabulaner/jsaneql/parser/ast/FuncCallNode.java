package de.pabulaner.jsaneql.parser.ast;

import java.util.List;

public class FuncCallNode extends Node {

    /**
     * The target of the function.
     */
    private final Node target;

    /**
     * The function args.
     */
    private final List<Node> args;

    public FuncCallNode(Node target, List<Node> args) {
        super(Type.FUNC_CALL, null);

        this.target = target;
        this.args = args;
    }

    public Node getTarget() {
        return target;
    }

    public List<Node> getArgs() {
        return args;
    }
}
