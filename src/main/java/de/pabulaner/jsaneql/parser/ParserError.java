package de.pabulaner.jsaneql.parser;

import de.pabulaner.jsaneql.tokenizer.Token;

public class ParserError {

    private final Token token;

    private final String message;

    public ParserError(Token token, String message) {
        this.token = token;
        this.message = message;
    }

    public static ParserError unexpectedToken(Token actual) {
        return unexpectedToken(null, actual);
    }

    public static ParserError unexpectedToken(Token.Kind expected, Token actual) {
        String message = "Unexpected token: " + actual;

        if (expected != null) {
            message += ", expected: " + expected;
        }

        return new ParserError(actual,  message);
    }
}
