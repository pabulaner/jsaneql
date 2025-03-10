package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.ValueType;

public class ValueExpression implements Expression {

    private final ValueType type;

    private final String value;

    public ValueExpression(ValueType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public void generate(SQLWriter out) {
        if (type == ValueType.UNKNOWN) {
            out.write("NULL");
        } else {
            out.write("CAST(");
            out.writeString(value);
            out.write(" AS ");
            out.writeType(type);
            out.write(")");
        }
    }

    @Override
    public ValueType getType() {
        return type;
    }
}
