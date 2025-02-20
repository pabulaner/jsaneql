package de.pabulaner.jsaneql.parser;

import de.pabulaner.jsaneql.parser.ast.AccessNode;
import de.pabulaner.jsaneql.parser.ast.BinaryExpressionNode;
import de.pabulaner.jsaneql.parser.ast.CastNode;
import de.pabulaner.jsaneql.parser.ast.FuncArgNode;
import de.pabulaner.jsaneql.parser.ast.FuncCallNode;
import de.pabulaner.jsaneql.parser.ast.ListNode;
import de.pabulaner.jsaneql.parser.ast.Node;
import de.pabulaner.jsaneql.parser.ast.QueryBodyNode;
import de.pabulaner.jsaneql.parser.ast.TokenNode;
import de.pabulaner.jsaneql.parser.ast.UnaryExpression;
import de.pabulaner.jsaneql.tokenizer.Token;

import java.util.LinkedList;
import java.util.List;

public class Parser {

    private int index;

    private List<Token> source;

    private List<ParserError> errors;

    public Parser() {
        this.index = 0;
        this.source = null;
        this.errors = null;
    }

    public Node parse(List<Token> source) {
        this.index = 0;
        this.source = source;
        this.errors = new LinkedList<>();

        if (peek() != Token.END) {
            return parseQueryBody();
        } else {
            errors.add(new ParserError(peek(), "No query found"));
            return null;
        }
    }

    private QueryBodyNode parseQueryBody() {
        return new QueryBodyNode(parseExpression());
    }

    private Node parseExpression() {
        return parseExpression(0);
    }

    private Node parseExpression(int minPrec) {
        Node lhs = parsePrimaryExpression();

        while (true) {
            Token token = peek();
            int prec = OperatorPrecedence.get(token.getKind());

            if (prec == OperatorPrecedence.END || prec < minPrec) {
                return lhs;
            }

            next();

            switch (token.getKind()) {
                case DOT: lhs = new AccessNode(lhs, parseExpression()); break;
                case CAST: lhs = new CastNode(lhs, parseExpression()); break;
                default: lhs = new BinaryExpressionNode(token, lhs, parseExpression(prec + 1)); break;
            }
        }
    }

    private Node parsePrimaryExpression() {
        Token token = next();
        Node node;

        switch (token.getKind()) {
            case IDENT:
                if (peekIf(Token.Kind.LPAREN)) {
                    return parseFuncCallNode(new TokenNode(token));
                }
                System.out.println("IDENT" + token.getValue());
            case INTEGER:
            case FLOAT:
            case FALSE:
            case TRUE:
            case NULL:
                return new TokenNode(token);
            case ADD:
            case SUB:
            case NOT:
                return new UnaryExpression(token, parseExpression());
            case QUOTE:
                node = new TokenNode(expectNext(Token.Kind.STRING));
                expectNext(Token.Kind.QUOTE);
                return node;
            case LPAREN:
                node = parseExpression();
                expectNext(Token.Kind.RPAREN);
                return node;
        }

        errors.add(ParserError.unexpectedToken(token));
        return null;
    }

    private Node parseFuncCallNode(Node target) {
        expectNext(Token.Kind.LPAREN);

        List<Node> args = new LinkedList<>();
        forEach(Token.Kind.RPAREN, () -> args.add(parseFuncArg()));

        expectNext(Token.Kind.RPAREN);
        return new FuncCallNode(target, args);
    }

    private Node parseFuncArg() {
        Token name = null;

        Node node;
        FuncArgNode.SubType type;

        if (peekIf(Token.Kind.IDENT, Token.Kind.ASSIGN)) {
            name = next();
            next();
        }

        if (nextIf(Token.Kind.LCURLY)) {
            List<Node> values = new LinkedList<>();
            forEach(Token.Kind.RCURLY, () -> {
                Token subName = null;

                if (peekIf(Token.Kind.IDENT, Token.Kind.ASSIGN)) {
                    subName = next();
                    next();
                    System.out.println("assign");
                }

                values.add(new FuncArgNode(null, FuncArgNode.SubType.FLAT, subName, parseExpression()));
            });

            node = new ListNode(null, values);
            type = FuncArgNode.SubType.LIST;

            expectNext(Token.Kind.RCURLY);
        } else {
            node = parseExpression();
            type = FuncArgNode.SubType.FLAT;
        }

        return new FuncArgNode(null, type, name, node);
    }

    private void forEach(Token.Kind end, Runnable callback) {
        boolean first = true;

        while (!peekIf(end) && !peekIf(Token.Kind.END)) {
            if (first) {
                first = false;
            } else {
                expectNext(Token.Kind.COMMA);
            }

            callback.run();
        }
    }

    private Token next() {
        return this.index < source.size()
                ? source.get(this.index++)
                : Token.END;
    }

    private boolean nextIf(Token.Kind value) {
        return index < source.size()
                && source.get(index).getKind() == value
                && index++ > -1;
    }

    private Token peek() {
        return this.index < source.size()
                ? source.get(this.index)
                : Token.END;
    }

    private boolean peekIf(Token.Kind... value) {
        boolean result = true;

        for (int i = 0; i < value.length; i++) {
            int offset = index + i;

            result &= (offset < source.size() && source.get(offset).getKind() == value[i])
                    || (offset >= source.size() && value[i] == Token.Kind.END);
        }

        return result;
    }

    private Token expectNext(Token.Kind kind) {
        Token actual = next();

        if (actual.getKind() != kind) {
            errors.add(ParserError.unexpectedToken(kind, actual));
            return null;
        }

        return actual;
    }
}
