package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.tokenizer.Token;

public class FuncArgNode extends Node {

    public enum SubType {

        FLAT,
        LIST,
    }

    /**
     * The type of the argument.
     */
    private final SubType subType;

    /**
     * The name of the argument, if any.
     */
    private final Token name;

    /**
     * The value of the argument.
     */
    private final Node value;

    public FuncArgNode(Token token, SubType subType, Token name, Node value) {
        super(Type.FUNC_ARG, token);

        this.subType = subType;
        this.name = name;
        this.value = value;
    }
}
