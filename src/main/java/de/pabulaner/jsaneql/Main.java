package de.pabulaner.jsaneql;

import de.pabulaner.jsaneql.parser.Parser;
import de.pabulaner.jsaneql.parser.ast.Node;
import de.pabulaner.jsaneql.tokenizer.Tokenizer;

public class Main {

    public static void main(String[] args) {
        String source = """
                lineitem
                    .filter(l_shipdate <= '1998-12-01'::date - '90 days'::interval)
                    .groupby({l_returnflag, l_linestatus}, {
                        sum_qty:=sum(l_quantity),
                        sum_base_price:=sum(l_extendedprice),
                        sum_disc_price:=sum(l_extendedprice * (1 - l_discount)),
                        sum_charge:=sum(l_extendedprice * (1 - l_discount) * (1 + l_tax)),
                        avg_qty:=avg(l_quantity),
                        avg_price:=avg(l_extendedprice),
                        avg_disc:=avg(l_discount),
                        count_order:=count()
                    })
                    .orderby({l_returnflag, l_linestatus})
                """;

        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser();

        Node node = parser.parse(tokenizer.parse(source));
    }
}
