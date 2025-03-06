package de.pabulaner.jsaneql.parser;

import de.pabulaner.jsaneql.tokenizer.Token;

public class ParserError {

    private final Token token;

    private final String message;

    private ParserError(Token token, String message) {
        this.token = token;
        this.message = message;
    }

    public static ParserError create(Token token) {
        String message = "Unexpected token: " + token.getValue();
        return new ParserError(token,  message);
    }

    public String getMessage() {
        return message;
    }
}
