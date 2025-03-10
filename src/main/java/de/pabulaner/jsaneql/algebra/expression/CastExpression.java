package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.ValueType;

public class CastExpression implements Expression {

    private final Expression value;

    private final ValueType type;

    public CastExpression(Expression value, ValueType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public void generate(SQLWriter out) {
        out.write("CAST(");
        value.generate(out);
        out.write(" AS ");
        out.writeType(type);
        out.write(")");
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
