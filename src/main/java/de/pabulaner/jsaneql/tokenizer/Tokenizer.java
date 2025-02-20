package de.pabulaner.jsaneql.tokenizer;

import de.pabulaner.jsaneql.util.StringView;

import java.util.LinkedList;
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
     * Constructs a new tokenizer.
     */
    public Tokenizer() {
        this.index = 0;
        this.source = null;
    }

    /**
     * Returns a list containing the parsed tokens.
     *
     * @param source the string to generate the tokens for.
     * @return a list containing the parsed tokens.
     */
    public List<Token> parse(String source) {
        this.index = 0;
        this.source = source;

        List<Token> result = new LinkedList<>();
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
     *
     * @param begin the first char of the token used to decide how
     *              to parse it.
     * @return a single parsed token
     */
    private Token parseToken(char begin) {
        if (isLetter(begin)) return parseIdent();
        if (isDigit(begin)) return parseNumber();
        if (isSpecial(begin)) return parseSpecial();

        return new Token(Token.Kind.INVALID, new StringView(source, index, ++index));
    }

    /**
     * Returns a list of tokens for a string sequence.
     *
     * @return a list of tokens for a string sequence
     */
    private List<Token> parseStringTokens() {
        List<Token> result = new LinkedList<>();
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
     *
     * @return an identifier token
     */
    private Token parseIdent() {
        int begin = index++;

        while (isLetter(peek())) {
            next();
        }

        Token.Kind kind = Token.Kind.IDENT;
        StringView value = new StringView(source, begin, index);

        switch (value.toString()) {
            case "false": kind = Token.Kind.FALSE; break;
            case "true": kind = Token.Kind.TRUE; break;
            case "null": kind = Token.Kind.NULL; break;
        }

        return new Token(kind, value);
    }

    /**
     * Returns a number token.
     *
     * @return a number token
     */
    private Token parseNumber() {
        Token.Kind kind = Token.Kind.INTEGER;
        int begin = index++;

        while (isDigit(peek())) {
            next();
        }

        if (isDot(peek())) {
            kind = Token.Kind.FLOAT;
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
     *
     * @return a special token
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
     *
     * @param value the value to check.
     * @return true, if the provided value is a whitespace
     */
    private boolean isWhitespace(char value) {
        return value == ' ' || value == '\t' || value == '\n';
    }

    /**
     * Returns true, if the provided value is a letter.
     *
     * @param value the value to check.
     * @return true, if the provided value is a letter
     */
    private boolean isLetter(char value) {
        return (value >= 'a' && value <= 'z')
                || (value >= 'A' && value <= 'Z')
                || value == '_';
    }

    /**
     * Returns true, if the provided value is a digit.
     *
     * @param value the value to check.
     * @return true, if the provided value is a digit
     */
    private boolean isDigit(char value) {
        return value >= '0' && value <= '9';
    }

    /**
     * Returns true, if the provided value is a quote.
     *
     * @param value the value to check.
     * @return true, if the provided value is a quote
     */
    private boolean isQuote(char value) {
        return value == '\'';
    }

    /**
     * Returns true, if the provided value is a dot.
     *
     * @param value the value to check.
     * @return true, if the provided value is a dot
     */
    private boolean isDot(char value) {
        return value == '.';
    }

    /**
     * Returns true, if the provided value is a special character.
     *
     * @param value the value to check.
     * @return true, if the provided value is a special character
     */
    private boolean isSpecial(char value) {
        String special = "+-*/%!=<>&|.,:(){}";
        return special.contains(String.valueOf(value));
    }

    /**
     * Returns true, if the provided value is not the end.
     *
     * @param value the value to check.
     * @return true, if the provided value is not the end
     */
    private boolean isNotEnd(char value) {
        return value != END;
    }

    /**
     * Returns the next or end token and increments the index.
     *
     * @return the next or end token
     */
    private char next() {
        return this.index < source.length()
                ? source.charAt(this.index++)
                : END;
    }

    /**
     * Returns true if the next char is equal to the provided char
     * and only then increments the index.
     *
     * @param value the value to compare to.
     * @return the next or end token
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
     *
     * @return the next or end token
     */
    private char peek() {
        return this.index < source.length()
                ? source.charAt(this.index)
                : END;
    }
}
