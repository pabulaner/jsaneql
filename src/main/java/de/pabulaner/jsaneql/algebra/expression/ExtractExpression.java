package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.ValueType;

public class ExtractExpression implements Expression {

    public enum Part {

        YEAR,
        MONTH,
        DAY,
    }

    private final Expression input;

    private final Part part;

    public ExtractExpression(Expression input, Part part) {
        this.input = input;
        this.part = part;
    }

    @Override
    public void generate(SQLWriter out) {
        out.write("EXTRACT(");

        switch (part) {
            case YEAR: out.write("YEAR"); break;
            case MONTH: out.write("MONTH"); break;
            case DAY: out.write("DAY"); break;
        }

        out.write(" FROM ");
        input.generateOperand(out);
        out.write(")");
    }

    @Override
    public ValueType getType() {
        return ValueType.INTEGER;
    }
}
