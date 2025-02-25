package de.pabulaner.jsaneql.tokenizer;

import de.pabulaner.jsaneql.util.StringView;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    /**
     * The character indicating that the end of the source is reached.
     */
    private static final char END = 0;

    /**
     * The index inside the source string.
     */
    private int index;

    /**
     * The source for tokenizing.
     */
    private String source;

    /**
     * The constructor.
     */
    public Tokenizer() {
        index = 0;
        source = null;
    }

    /**
     * Returns a list containing the parsed tokens.
     */
    public List<Token> parse(String source) {
        this.index = 0;
        this.source = source;

        List<Token> result = new ArrayList<>();
        char value;

        while (isNotEnd(value = peek())) {
            if (isWhitespace(value)) {
                next();
            } else {
                if (isQuote(value)) {
                    result.addAll(parseStringTokens());
                } else {
                    result.add(parseToken(value));
                }
            }
        }

        return result;
    }

    /**
     * Returns a single parsed token.
     */
    private Token parseToken(char begin) {
        if (isLetter(begin)) return parseIdent();
        if (isDigit(begin)) return parseNumber();
        if (isSpecial(begin)) return parseSpecial();

        return new Token(Token.Kind.INVALID, new StringView(source, index, ++index));
    }

    /**
     * Returns a list of tokens for a string sequence.
     */
    private List<Token> parseStringTokens() {
        List<Token> result = new ArrayList<>();
        result.add(new Token(Token.Kind.QUOTE, new StringView(source, index, ++index)));
        
        int begin = index;
        
        while (!isQuote(peek()) && isNotEnd(peek())) {
            char value = next();

            if (value == '\\') {
                next();
            }
        }
        
        result.add(new Token(Token.Kind.STRING, new StringView(source, begin, index)));
        
        if (isQuote(peek())) {
            result.add(new Token(Token.Kind.QUOTE, new StringView(source, index, ++index)));
        }
        
        return result;
    }

    /**
     * Returns an identifier token.
     */
    private Token parseIdent() {
        int begin = index++;

        while (isLetter(peek())) {
            next();
        }

        Token.Kind kind = Token.Kind.IDENT;
        StringView value = new StringView(source, begin, index);

        switch (value.toString()) {
            case "false":
            case "true": kind = Token.Kind.BOOLEAN; break;
            case "null": kind = Token.Kind.NULL; break;
        }

        return new Token(kind, value);
    }

    /**
     * Returns a number token.
     */
    private Token parseNumber() {
        Token.Kind kind = Token.Kind.INTEGER;
        int begin = index++;

        while (isDigit(peek())) {
            next();
        }

        if (isDot(peek()) && isDigit(peek(1))) {
            kind = Token.Kind.DECIMAL;
            next();

            if (!isDigit(peek())) {
                kind = Token.Kind.INVALID;
            }

            while (isDigit(peek())) {
                next();
            }
        }

        return new Token(kind, new StringView(source, begin, index));
    }

    /**
     * Returns a special token.
     */
    private Token parseSpecial() {
        Token.Kind kind = Token.Kind.INVALID;
        
        int begin = index;
        char value = next();
        
        switch (value) {
            case '+': kind = Token.Kind.ADD; break;
            case '-': kind = Token.Kind.SUB; break;
            case '*': kind = Token.Kind.MUL; break;
            case '/': kind = Token.Kind.DIV; break;
            case '%': kind = Token.Kind.MOD; break;
            case '^': kind = Token.Kind.POW; break;
            case '!': kind = Token.Kind.NOT; break;
            case '=': kind = Token.Kind.EQ; break;
            case '<': kind = nextIf('=') 
                    ? Token.Kind.LE 
                    : nextIf('>') 
                    ? Token.Kind.NEQ 
                    : Token.Kind.LT; break;
            case '>': kind = nextIf('=')
                    ? Token.Kind.GE
                    : Token.Kind.GT; break;
            case '&': kind = nextIf('&')
                    ? Token.Kind.AND
                    : Token.Kind.INVALID; break;
            case '|': kind = nextIf('|')
                    ? Token.Kind.OR
                    : Token.Kind.INVALID; break;
            case '.': kind = Token.Kind.DOT; break;
            case ',': kind = Token.Kind.COMMA; break;
            case ':': kind = nextIf('=')
                    ? Token.Kind.ASSIGN
                    : nextIf(':')
                    ? Token.Kind.CAST
                    : Token.Kind.INVALID; break;
            case '(': kind = Token.Kind.LPAREN; break;
            case ')': kind = Token.Kind.RPAREN; break;
            case '{': kind = Token.Kind.LCURLY; break;
            case '}': kind = Token.Kind.RCURLY; break;
        }
        
        return new Token(kind, new StringView(source, begin, index));
    }

    /**
     * Returns true, if the provided value is a whitespace.
     */
    private boolean isWhitespace(char value) {
        return value == ' ' || value == '\t' || value == '\n';
    }

    /**
     * Returns true, if the provided value is a letter.
     */
    private boolean isLetter(char value) {
        return (value >= 'a' && value <= 'z')
                || (value >= 'A' && value <= 'Z')
                || value == '_';
    }

    /**
     * Returns true, if the provided value is a digit.
     */
    private boolean isDigit(char value) {
        return value >= '0' && value <= '9';
    }

    /**
     * Returns true, if the provided value is a quote.
     */
    private boolean isQuote(char value) {
        return value == '\'';
    }

    /**
     * Returns true, if the provided value is a dot.
     */
    private boolean isDot(char value) {
        return value == '.';
    }

    /**
     * Returns true, if the provided value is a special character.
     */
    private boolean isSpecial(char value) {
        String special = "+-*/%!=<>&|.,:(){}";
        return special.contains(String.valueOf(value));
    }

    /**
     * Returns true, if the provided value is not the end.
     */
    private boolean isNotEnd(char value) {
        return value != END;
    }

    /**
     * Returns the next or end token and increments the index.
     */
    private char next() {
        return index < source.length()
                ? source.charAt(index++)
                : END;
    }

    /**
     * Returns true if the next char is equal to the provided char
     * and only then increments the index.
     */
    private boolean nextIf(char value) {
        if (index < source.length()) {
            if (source.charAt(index) == value) {
                index++;
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the next or end token, but does not increment the index.
     */
    private char peek() {
        return peek(0);
    }

    /**
     * Returns the next or end token, but does not increment the index.
     */
    private char peek(int offset) {
        return index + offset < source.length()
                ? source.charAt(index + offset)
                : END;
    }
}
