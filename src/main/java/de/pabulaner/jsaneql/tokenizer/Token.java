package de.pabulaner.jsaneql.tokenizer;

import de.pabulaner.jsaneql.util.StringView;

import java.util.Objects;

public class Token {

    /**
     * The kind of known tokens.
     */
    public enum Kind {

        /**
         * Values
         */
        IDENT,
        STRING,
        INTEGER,
        FLOAT,
        FALSE,
        TRUE,
        NULL,

        /**
         * Logic
         */
        ADD,
        SUB,
        MUL,
        DIV,
        MOD,
        POW,
        NOT,
        EQ,
        NEQ,
        LT,
        GT,
        LE,
        GE,
        AND,
        OR,

        /**
         * Special
         */
        DOT,
        COMMA,
        ASSIGN,
        CAST,
        QUOTE,
        LPAREN,
        RPAREN,
        LCURLY,
        RCURLY,
        NEW_LINE,
        INVALID,
        END,
    }

    /**
     * The end token.
     */
    public static final Token END = new Token(Kind.END, StringView.EMPTY);

    /**
     * The kind of the token.
     */
    private final Kind kind;

    /**
     * The textual value of the token.
     */
    private final StringView value;

    public Token(Kind kind, StringView value) {
        this.kind = kind;
        this.value = value;
    }

    public Kind getKind() {
        return kind;
    }

    public StringView getValue() {
        return value;
    }

    public int getSourceLine() {
        int line = 1;

        for (int i = 0; i < value.getBegin(); i++) {
            if (value.getSource().charAt(i) == '\n') {
                line += 1;
            }
        }

        return line;
    }

    public int getSourceColumn() {
        int column = 0;

        for (int i = value.getBegin(); i >= 0; i--) {
            if (value.getSource().charAt(i) == '\n') {
                break;
            } else {
                column += 1;
            }
        }

        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass().equals(getClass())) {
            Token casted = (Token) obj;
            return casted.kind == kind && casted.value.equals(value);
        }

        return false;
    }

    @Override
    public String toString() {
        String result = kind.toString();

        if (value != null) {
            result += "(" + value + ")";
        }

        return result;
    }
}
