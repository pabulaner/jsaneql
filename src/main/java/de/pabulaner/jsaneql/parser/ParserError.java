package de.pabulaner.jsaneql.parser;

import de.pabulaner.jsaneql.tokenizer.Token;

public class ParserError {

    private final String message;

    private ParserError(String message) {
        this.message = message;
    }

    public static ParserError create(Token token) {
        String message = "Unexpected token: " + token.getValue();
        return new ParserError(message);
    }

    public String getMessage() {
        return message;
    }
}
