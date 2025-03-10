package de.pabulaner.jsaneql;

import de.pabulaner.jsaneql.compile.CompileException;
import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.parser.Parser;
import de.pabulaner.jsaneql.parser.ast.QueryNode;
import de.pabulaner.jsaneql.schema.TableColumn;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.schema.tpch.TPCHDatabase;
import de.pabulaner.jsaneql.semana.SemanticAnalyzer;
import de.pabulaner.jsaneql.semana.result.ExpressionResult;
import de.pabulaner.jsaneql.sql.SQLDatabase;
import de.pabulaner.jsaneql.sql.SQLResult;
import de.pabulaner.jsaneql.sql.SQLTable;
import de.pabulaner.jsaneql.tokenizer.Token;
import de.pabulaner.jsaneql.tokenizer.Tokenizer;

import java.sql.SQLData;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, CompileException {
        String source = "lineitem\n" +
                "    .filter(l_shipdate <= '1998-12-01'::date - '90 days'::interval)\n" +
                "    .groupby({l_returnflag, l_linestatus}, {\n" +
                "        sum_qty:=sum(l_quantity),\n" +
                "        sum_base_price:=sum(l_extendedprice),\n" +
                "        sum_disc_price:=sum(l_extendedprice * (1 - l_discount)),\n" +
                "        sum_charge:=sum(l_extendedprice * (1 - l_discount) * (1 + l_tax)),\n" +
                "        avg_qty:=avg(l_quantity),\n" +
                "        avg_price:=avg(l_extendedprice),\n" +
                "        avg_disc:=avg(l_discount)\n" +
                "    })\n" +
                "    .orderby({l_returnflag, l_linestatus})" +
                "   .map({hello:=4 * 2})";

        SQLDatabase db = new SQLDatabase();
        SQLTable table = db.addTable("customer", new TableColumn("name", ValueType.TEXT), new TableColumn("age", ValueType.INTEGER));
        table.addRow(Value.ofString("paul"), Value.ofInteger(10L));
        table.addRow(Value.ofString("peter"), Value.ofInteger(15L));

//        Tokenizer tokenizer = new Tokenizer();
//        Parser parser = new Parser();
//        SemanticAnalyzer analyzer = new SemanticAnalyzer(db);
//
//        List<Token> tokens = tokenizer.parse(source);
//        QueryNode ast = parser.parse(tokens);
//        ExpressionResult tree = analyzer.parse(ast);
//
//        SQLWriter out = new SQLWriter();
//        tree.table().generate(out);

        SQLResult result = db.execSane("customer.map({name:=age*3})");
        System.out.println(result);
    }
}
