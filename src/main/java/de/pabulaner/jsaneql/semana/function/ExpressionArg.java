package de.pabulaner.jsaneql.semana.function;

import de.pabulaner.jsaneql.semana.result.ExpressionResult;

public class ExpressionArg {

    private final String name;

    private final ExpressionResult result;

    public ExpressionArg(String name, ExpressionResult result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public ExpressionResult getResult() {
        return result;
    }
}
