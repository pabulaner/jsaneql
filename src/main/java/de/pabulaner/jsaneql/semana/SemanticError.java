package de.pabulaner.jsaneql.semana;

import de.pabulaner.jsaneql.util.StringView;

public class SemanticError {

    private final StringView view;

    private final String message;

    private SemanticError(StringView view, String message) {
        this.view = view;
        this.message = message;
    }

    public static SemanticError create(StringView view, String message) {
        return new SemanticError(view, message);
    }
}
