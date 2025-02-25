package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;

import java.util.List;

public class MapOperator implements Operator {

    public class Entry {

        private final IU iu;

        private final Expression value;

        public Entry(IU iu, Expression value) {
            this.iu = iu;
            this.value = value;
        }
    }

    private final Operator input;

    private final List<Entry> computations;

    public MapOperator(Operator input, List<Entry> computations) {
        this.input = input;
        this.computations = computations;
    }
}
