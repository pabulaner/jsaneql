package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.tokenizer.Token;

public class TokenNode extends Node {

    public TokenNode(Token token) {
        super(Type.TOKEN, token);
    }
}
