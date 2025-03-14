package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.Column;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.HashMap;
import java.util.Map;

public class TPCHDatabase implements Database {

    private final Map<String, Table> tables;

    public TPCHDatabase() {
        tables = new HashMap<>();
        
        tables.put("part", new TPCHTable(new Column("p_partkey", ValueType.INTEGER), new Column("p_name", ValueType.TEXT), new Column("p_mfgr", ValueType.TEXT), new Column("p_brand", ValueType.TEXT), new Column("p_type", ValueType.TEXT), new Column("p_size", ValueType.INTEGER), new Column("p_container", ValueType.TEXT), new Column("p_retailprice", ValueType.DECIMAL), new Column("p_comment", ValueType.TEXT)));
        tables.put("region", new TPCHTable(new Column("r_regionkey", ValueType.INTEGER), new Column("r_name", ValueType.TEXT), new Column("r_comment", ValueType.TEXT)));
        tables.put("nation", new TPCHTable(new Column("n_nationkey", ValueType.INTEGER), new Column("n_name", ValueType.TEXT), new Column("n_regionkey", ValueType.INTEGER), new Column("n_comment", ValueType.TEXT)));
        tables.put("supplier", new TPCHTable(new Column("s_suppkey", ValueType.INTEGER), new Column("s_name", ValueType.TEXT), new Column("s_address", ValueType.TEXT), new Column("s_nationkey", ValueType.INTEGER), new Column("s_phone", ValueType.TEXT), new Column("s_acctbal", ValueType.DECIMAL), new Column("s_comment", ValueType.TEXT)));
        tables.put("partsupp", new TPCHTable(new Column("ps_partkey", ValueType.INTEGER), new Column("ps_suppkey", ValueType.INTEGER), new Column("ps_availqty", ValueType.INTEGER), new Column("ps_supplycost", ValueType.DECIMAL), new Column("ps_comment", ValueType.TEXT)));
        tables.put("customer", new TPCHTable(new Column("c_custkey", ValueType.INTEGER), new Column("c_name", ValueType.TEXT), new Column("c_address", ValueType.TEXT), new Column("c_nationkey", ValueType.INTEGER), new Column("c_phone", ValueType.TEXT), new Column("c_acctbal", ValueType.DECIMAL), new Column("c_mktsegment", ValueType.TEXT), new Column("c_comment", ValueType.TEXT)));
        tables.put("orders", new TPCHTable(new Column("o_orderkey", ValueType.INTEGER), new Column("o_custkey", ValueType.INTEGER), new Column("o_orderstatus", ValueType.TEXT), new Column("o_totalprice", ValueType.DECIMAL), new Column("o_orderdate", ValueType.DATE), new Column("o_orderpriority", ValueType.TEXT), new Column("o_clerk", ValueType.TEXT), new Column("o_shippriority", ValueType.INTEGER), new Column("o_comment", ValueType.TEXT)));
        tables.put("lineitem", new TPCHTable(new Column("l_orderkey", ValueType.INTEGER), new Column("l_partkey", ValueType.INTEGER), new Column("l_suppkey", ValueType.INTEGER), new Column("l_linenumber", ValueType.INTEGER), new Column("l_quantity", ValueType.DECIMAL), new Column("l_extendedprice", ValueType.DECIMAL), new Column("l_discount", ValueType.DECIMAL), new Column("l_tax", ValueType.DECIMAL), new Column("l_returnflag", ValueType.TEXT), new Column("l_linestatus", ValueType.TEXT), new Column("l_shipdate", ValueType.DATE), new Column("l_commitdate", ValueType.DATE), new Column("l_receiptdate", ValueType.DATE), new Column("l_shipinstruct", ValueType.TEXT), new Column("l_shipmode", ValueType.TEXT), new Column("l_comment", ValueType.TEXT)));
    }

    @Override
    public Table getTable(String name) {
        return tables.get(name);
    }
}
