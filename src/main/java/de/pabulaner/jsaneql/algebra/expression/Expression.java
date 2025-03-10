package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.ValueType;

public interface Expression {

    void generate(SQLWriter out);

    default void generateOperand(SQLWriter out) {
        out.write("(");
        generate(out);
        out.write(")");
    }

    ValueType getType();
}
