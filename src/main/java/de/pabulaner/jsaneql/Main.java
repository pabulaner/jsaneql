package de.pabulaner.jsaneql;

import de.pabulaner.jsaneql.parser.Parser;
import de.pabulaner.jsaneql.parser.ast.QueryNode;
import de.pabulaner.jsaneql.schema.tpch.TPCHDatabase;
import de.pabulaner.jsaneql.semana.result.Result;
import de.pabulaner.jsaneql.semana.SemanticAnalyzer;
import de.pabulaner.jsaneql.tokenizer.Token;
import de.pabulaner.jsaneql.tokenizer.Tokenizer;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String source = "lineitem\n" +
                        "    .filter(l_shipdate <= '1998-12-01'::date - '90 days'::interval)\n" +
                        "    .groupby({l_returnflag, l_linestatus}, {\n" +
                        "        sum_qty:=sum(l_quantity),\n" +
                        "        sum_base_price:=sum(l_extendedprice),\n" +
                        "        sum_disc_price:=sum(l_extendedprice * (1 - l_discount)),\n" +
                        "        sum_charge:=sum(l_extendedprice * (1 - l_discount) * (1 + l_tax)),\n" +
                        "        avg_qty:=avg(l_quantity),\n" +
                        "        avg_price:=avg(l_extendedprice),\n" +
                        "        avg_disc:=avg(l_discount),\n" +
                        "        count_order:=count()\n" +
                        "    })\n" +
                        "    .orderby({l_returnflag, l_linestatus})\n";

        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser();
        SemanticAnalyzer analyzer = new SemanticAnalyzer(new TPCHDatabase());

        List<Token> tokens = tokenizer.parse("(10 * 30).desc()");
        QueryNode ast = parser.parse(tokens);
        Result result = analyzer.parse(ast);

        System.out.println(result);
    }
}
