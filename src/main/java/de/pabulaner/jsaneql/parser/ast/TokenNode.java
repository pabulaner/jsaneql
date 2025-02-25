package de.pabulaner.jsaneql.parser.ast;

import de.pabulaner.jsaneql.tokenizer.Token;
import de.pabulaner.jsaneql.util.StringView;

public class TokenNode extends Node {

    private final Token token;

    public TokenNode(Token token) {
        super(Type.TOKEN);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public StringView getView() {
        return token != null ? token.getValue() : StringView.EMPTY;
    }
}
