package de.pabulaner.jsaneql.semana.function;

import de.pabulaner.jsaneql.semana.result.Result;

public class ExpressionArg {

    private final String name;

    private final Result result;

    public ExpressionArg(String name, Result result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public Result getResult() {
        return result;
    }
}
