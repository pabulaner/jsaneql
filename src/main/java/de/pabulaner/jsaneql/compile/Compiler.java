package de.pabulaner.jsaneql.compile;

import de.pabulaner.jsaneql.parser.Parser;
import de.pabulaner.jsaneql.parser.ast.QueryNode;
import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.semana.SemanticAnalyzer;
import de.pabulaner.jsaneql.semana.result.ExpressionResult;
import de.pabulaner.jsaneql.tokenizer.Token;
import de.pabulaner.jsaneql.tokenizer.Tokenizer;

import java.util.List;

public class Compiler {

    private final Tokenizer tokenizer;

    private final Parser parser;

    private final SemanticAnalyzer analyzer;

    public Compiler(Database db) {
        tokenizer = new Tokenizer();
        parser = new Parser();
        analyzer = new SemanticAnalyzer(db);
    }

    public ExpressionResult compile(String query) throws CompileException {
        List<Token> tokens = tokenizer.parse(query);
        QueryNode ast = parser.parse(tokens);

        if (!parser.getErrors().isEmpty()) {
            throw new CompileException(parser.getErrors().get(0).getMessage());
        }

        try {
            return analyzer.parse(ast);
        } catch (RuntimeException e) {
            throw new CompileException(e.getMessage());
        }
    }
}
