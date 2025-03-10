package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.compile.CompileException;
import de.pabulaner.jsaneql.compile.Compiler;
import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.schema.TestDatabase;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.algebra.Column;
import de.pabulaner.jsaneql.semana.result.ExpressionResult;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ScanOperatorTest {

    @Test
    public void testScanOperator() throws CompileException {
        Database db = new TestDatabase();
        ExpressionResult tree = new Compiler(db).compile("accounts.orderby({username.desc()})");
        Map<IU, Value> row;

        for (Column column : tree.binding().getColumns()) {
            System.out.print("'" + column.getName() + "' ");
        }

        System.out.println();

        while ((row = tree.table().next()) != null) {
            for (Column column : tree.binding().getColumns()) {
                System.out.print("'" + row.get(column.getIU()) + "' ");
            }

            System.out.println();
        }
    }
}