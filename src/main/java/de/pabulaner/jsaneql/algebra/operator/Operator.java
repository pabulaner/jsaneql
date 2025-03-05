package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.TableRow;
import de.pabulaner.jsaneql.schema.Value;

import java.util.Iterator;
import java.util.Map;

public interface Operator {

    Map<IU, Value> next();
}
