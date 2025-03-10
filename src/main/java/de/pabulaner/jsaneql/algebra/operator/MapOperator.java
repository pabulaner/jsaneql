package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.Expression;
import de.pabulaner.jsaneql.compile.SQLWriter;

import java.util.List;

public class MapOperator implements Operator {

    public static class Entry {

        private final IU iu;

        private final Expression value;

        public Entry(IU iu, Expression value) {
            this.iu = iu;
            this.value = value;
        }

        public IU getIU() {
            return iu;
        }

        public Expression getValue() {
            return value;
        }
    }

    private final Operator input;

    private final List<Entry> computations;

    public MapOperator(Operator input, List<Entry> computations) {
        this.input = input;
        this.computations = computations;
    }

    @Override
    public void generate(SQLWriter out) {
        out.write("(SELECT *");

        for (Entry computation : computations) {
            out.write(", ");
            computation.getValue().generate(out);
            out.write (" AS ");
            out.writeIU(computation.getIU());
        }

        out.write(" FROM ");
        input.generate(out);
        out.write(" s)");
    }
}
