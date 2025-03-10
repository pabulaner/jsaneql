package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.compile.SQLWriter;

public interface Operator {

    void generate(SQLWriter out);
}
