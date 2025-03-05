package de.pabulaner.jsaneql.parser;

import de.pabulaner.jsaneql.algebra.expression.BinaryExpression;
import de.pabulaner.jsaneql.algebra.expression.UnaryExpression;
import de.pabulaner.jsaneql.parser.ast.AccessNode;
import de.pabulaner.jsaneql.parser.ast.BinaryNode;
import de.pabulaner.jsaneql.parser.ast.CastNode;
import de.pabulaner.jsaneql.parser.ast.ArgNode;
import de.pabulaner.jsaneql.parser.ast.CallNode;
import de.pabulaner.jsaneql.parser.ast.ListNode;
import de.pabulaner.jsaneql.parser.ast.Node;
import de.pabulaner.jsaneql.parser.ast.QueryNode;
import de.pabulaner.jsaneql.parser.ast.TokenNode;
import de.pabulaner.jsaneql.parser.ast.UnaryNode;
import de.pabulaner.jsaneql.tokenizer.Token;

import java.util.ArrayList;
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

    public QueryNode parse(List<Token> source) {
        this.index = 0;
        this.source = source;
        this.errors = new ArrayList<>();

        if (peek() != Token.END) {
            return parseQueryBody();
        } else {
            reportError(peek());
            return null;
        }
    }

    private QueryNode parseQueryBody() {
        return new QueryNode(parseExpression());
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
            prec += 1;

            BinaryExpression.Operation operation = null;

            switch (token.getKind()) {
                case LPAREN: lhs = new CallNode(lhs, parseArgs()); expectNext(Token.Kind.RPAREN); break;
                case DOT: lhs = new AccessNode(lhs, parseExpression(prec)); break;
                case CAST: lhs = new CastNode(lhs, parseExpression(prec)); break;
                case ADD: operation = BinaryExpression.Operation.ADD; break;
                case SUB: operation = BinaryExpression.Operation.SUB; break;
                case MUL: operation = BinaryExpression.Operation.MUL; break;
                case DIV: operation = BinaryExpression.Operation.DIV; break;
                case MOD: operation = BinaryExpression.Operation.MOD; break;
                case POW: operation = BinaryExpression.Operation.POW; break;
                case EQ: operation = BinaryExpression.Operation.EQUALS; break;
                case NEQ: operation = BinaryExpression.Operation.NOT_EQUALS; break;
                case LT: operation = BinaryExpression.Operation.LESS; break;
                case GT: operation = BinaryExpression.Operation.GREATER; break;
                case LE: operation = BinaryExpression.Operation.LESS_EQUALS; break;
                case GE: operation = BinaryExpression.Operation.GREATER_EQUALS; break;
                case AND: operation = BinaryExpression.Operation.AND; break;
                case OR: operation = BinaryExpression.Operation.OR; break;
                default: reportError(token); break;
            }

            if (operation != null) {
                lhs = new BinaryNode(operation, lhs, parseExpression(prec));
            }
        }
    }

    private Node parsePrimaryExpression() {
        Token token = next();
        Node node;

        switch (token.getKind()) {
            case IDENT:
            case INTEGER:
            case DECIMAL:
            case BOOLEAN:
                return new TokenNode(token);
            case ADD:
                return new UnaryNode(UnaryExpression.Operation.ADD, parseExpression());
            case SUB:
                return new UnaryNode(UnaryExpression.Operation.SUB, parseExpression());
            case NOT:
                return new UnaryNode(UnaryExpression.Operation.NOT, parseExpression());
            case QUOTE:
                node = new TokenNode(expectNext(Token.Kind.STRING));
                expectNext(Token.Kind.QUOTE);
                return node;
            case LPAREN:
                node = parseExpression();
                expectNext(Token.Kind.RPAREN);
                return node;
        }

        reportError(token);
        return null;
    }

    private List<Node> parseArgs() {
        List<Node> argNodes = new ArrayList<>();
        forEach(Token.Kind.RPAREN, () -> argNodes.add(parseArg()));

        return argNodes;
    }

    private Node parseArg() {
        Node nameNode = null;
        Node node;
        boolean isList;

        if (peekKind() == Token.Kind.IDENT && peekKind(1) == Token.Kind.ASSIGN) {
            nameNode = new TokenNode(next());
            next();
        }

        if (peekKind() == Token.Kind.LCURLY) {
            List<Node> values = new ArrayList<>();
            next();

            forEach(Token.Kind.RCURLY, () -> {
                Node subNameNode = null;

                if (peekKind() == Token.Kind.IDENT && peekKind(1) == Token.Kind.ASSIGN) {
                    subNameNode = new TokenNode(next());
                    next();
                }

                values.add(new ArgNode(false, subNameNode, parseExpression()));
            });

            node = new ListNode(values);
            isList = true;

            expectNext(Token.Kind.RCURLY);
        } else {
            node = parseExpression();
            isList = false;
        }

        return new ArgNode(isList, nameNode, node);
    }

    private void forEach(Token.Kind end, Runnable callback) {
        boolean first = true;

        while (peekKind() != end && peekKind() != Token.Kind.END) {
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

    private Token peek() {
        return this.index < source.size()
                ? source.get(this.index)
                : Token.END;
    }

    private Token.Kind peekKind() {
        return peekKind(0);
    }

    private Token.Kind peekKind(int offset) {
        return index + offset < source.size()
                ? source.get(index + offset).getKind()
                : Token.Kind.END;
    }

    private Token expectNext(Token.Kind kind) {
        Token actual = next();

        if (actual.getKind() != kind) {
            reportError(actual);
            return actual;
        }

        return actual;
    }

    private void reportError(Token token) {
        errors.add(ParserError.create(token));
    }
}
