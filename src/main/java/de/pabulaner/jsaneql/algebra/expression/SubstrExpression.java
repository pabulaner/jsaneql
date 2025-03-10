package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.ValueType;

public class SubstrExpression implements Expression {

    private final Expression value;

    private final Expression from;

    private final Expression length;

    public SubstrExpression(Expression value, Expression from, Expression length) {
        this.value = value;
        this.from = from;
        this.length = length;
    }

    @Override
    public void generate(SQLWriter out) {
        out.write("SUBSTR(");
        value.generate(out);

        if (from != null) {
            out.write(" FROM ");
            from.generate(out);
        }

        if (length != null) {
            out.writeString(" FOR ");
            length.generate(out);
        }

        out.write(")");
    }

    @Override
    public ValueType getType() {
        return ValueType.TEXT;
    }
}
