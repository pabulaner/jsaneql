package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.Map;

public interface Expression {

    Value getValue(Map<IU, Value> row);

    ValueType getType();
}
