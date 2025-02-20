package de.pabulaner.jsaneql.parser;

import de.pabulaner.jsaneql.tokenizer.Token;

public class OperatorPrecedence {

    public static final int END = -1;

    public static int get(Token.Kind kind) {
        switch (kind) {
            case OR: return 1;
            case AND: return 2;
            case NOT: return 3;
            case EQ:
            case NEQ:
            case LT:
            case GT:
            case LE:
            case GE: return 4;
            case ADD:
            case SUB: return 5;
            case MUL:
            case DIV:
            case MOD: return 6;
            case POW: return 7;
            case LPAREN: return 8;
            case CAST: return 9;
            case DOT: return 10;
        }

        return END;
    }
}
