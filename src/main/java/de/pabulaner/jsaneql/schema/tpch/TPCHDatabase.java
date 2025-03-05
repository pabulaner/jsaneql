package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.ArrayList;
import java.util.List;

public class TPCHDatabase implements Database {

    private final List<Table> tables;

    public TPCHDatabase() {
        tables = new ArrayList<>();
        
        tables.add(new TPCHTable("part", new TPCHTableColumn("p_partkey", ValueType.INTEGER), new TPCHTableColumn("p_name", ValueType.STRING), new TPCHTableColumn("p_mfgr", ValueType.STRING), new TPCHTableColumn("p_brand", ValueType.STRING), new TPCHTableColumn("p_type", ValueType.STRING), new TPCHTableColumn("p_size", ValueType.INTEGER), new TPCHTableColumn("p_container", ValueType.STRING), new TPCHTableColumn("p_retailprice", ValueType.DECIMAL), new TPCHTableColumn("p_comment", ValueType.STRING)));
        tables.add(new TPCHTable("region", new TPCHTableColumn("r_regionkey", ValueType.INTEGER), new TPCHTableColumn("r_name", ValueType.STRING), new TPCHTableColumn("r_comment", ValueType.STRING)));
        tables.add(new TPCHTable("nation", new TPCHTableColumn("n_nationkey", ValueType.INTEGER), new TPCHTableColumn("n_name", ValueType.STRING), new TPCHTableColumn("n_regionkey", ValueType.INTEGER), new TPCHTableColumn("n_comment", ValueType.STRING)));
        tables.add(new TPCHTable("supplier", new TPCHTableColumn("s_suppkey", ValueType.INTEGER), new TPCHTableColumn("s_name", ValueType.STRING), new TPCHTableColumn("s_address", ValueType.STRING), new TPCHTableColumn("s_nationkey", ValueType.INTEGER), new TPCHTableColumn("s_phone", ValueType.STRING), new TPCHTableColumn("s_acctbal", ValueType.DECIMAL), new TPCHTableColumn("s_comment", ValueType.STRING)));
        tables.add(new TPCHTable("partsupp", new TPCHTableColumn("ps_partkey", ValueType.INTEGER), new TPCHTableColumn("ps_suppkey", ValueType.INTEGER), new TPCHTableColumn("ps_availqty", ValueType.INTEGER), new TPCHTableColumn("ps_supplycost", ValueType.DECIMAL), new TPCHTableColumn("ps_comment", ValueType.STRING)));
        tables.add(new TPCHTable("customer", new TPCHTableColumn("c_custkey", ValueType.INTEGER), new TPCHTableColumn("c_name", ValueType.STRING), new TPCHTableColumn("c_address", ValueType.STRING), new TPCHTableColumn("c_nationkey", ValueType.INTEGER), new TPCHTableColumn("c_phone", ValueType.STRING), new TPCHTableColumn("c_acctbal", ValueType.DECIMAL), new TPCHTableColumn("c_mktsegment", ValueType.STRING), new TPCHTableColumn("c_comment", ValueType.STRING)));
        tables.add(new TPCHTable("orders", new TPCHTableColumn("o_orderkey", ValueType.INTEGER), new TPCHTableColumn("o_custkey", ValueType.INTEGER), new TPCHTableColumn("o_orderstatus", ValueType.STRING), new TPCHTableColumn("o_totalprice", ValueType.DECIMAL), new TPCHTableColumn("o_orderdate", ValueType.DATE), new TPCHTableColumn("o_orderpriority", ValueType.STRING), new TPCHTableColumn("o_clerk", ValueType.STRING), new TPCHTableColumn("o_shippriority", ValueType.INTEGER), new TPCHTableColumn("o_comment", ValueType.STRING)));
        tables.add(new TPCHTable("lineitem", new TPCHTableColumn("l_orderkey", ValueType.INTEGER), new TPCHTableColumn("l_partkey", ValueType.INTEGER), new TPCHTableColumn("l_suppkey", ValueType.INTEGER), new TPCHTableColumn("l_linenumber", ValueType.INTEGER), new TPCHTableColumn("l_quantity", ValueType.DECIMAL), new TPCHTableColumn("l_extendedprice", ValueType.DECIMAL), new TPCHTableColumn("l_discount", ValueType.DECIMAL), new TPCHTableColumn("l_tax", ValueType.DECIMAL), new TPCHTableColumn("l_returnflag", ValueType.STRING), new TPCHTableColumn("l_linestatus", ValueType.STRING), new TPCHTableColumn("l_shipdate", ValueType.DATE), new TPCHTableColumn("l_commitdate", ValueType.DATE), new TPCHTableColumn("l_receiptdate", ValueType.DATE), new TPCHTableColumn("l_shipinstruct", ValueType.STRING), new TPCHTableColumn("l_shipmode", ValueType.STRING), new TPCHTableColumn("l_comment", ValueType.STRING)));
    }

    @Override
    public Table getTable(String name) {
        return tables.stream()
                .filter(table -> table.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Table> getTables() {
        return tables;
    }
}
