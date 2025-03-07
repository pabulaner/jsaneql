package de.pabulaner.jsaneql.algebra.operator;

import de.pabulaner.jsaneql.CompileException;
import de.pabulaner.jsaneql.Compiler;
import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.schema.TestDatabase;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.semana.binding.Column;
import de.pabulaner.jsaneql.semana.result.Result;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ScanOperatorTest {

    @Test
    public void testScanOperator() throws CompileException {
        Database db = new TestDatabase();
        Result tree = new Compiler(db).compile("accounts.orderby({username.desc()})");
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