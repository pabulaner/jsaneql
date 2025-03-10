package de.pabulaner.jsaneql.schema.tpch;

import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.TableColumn;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.HashMap;
import java.util.Map;

public class TPCHDatabase implements Database {

    private final Map<String, Table> tables;

    public TPCHDatabase() {
        tables = new HashMap<>();
        
        tables.put("part", new TPCHTable(new TableColumn("p_partkey", ValueType.INTEGER), new TableColumn("p_name", ValueType.TEXT), new TableColumn("p_mfgr", ValueType.TEXT), new TableColumn("p_brand", ValueType.TEXT), new TableColumn("p_type", ValueType.TEXT), new TableColumn("p_size", ValueType.INTEGER), new TableColumn("p_container", ValueType.TEXT), new TableColumn("p_retailprice", ValueType.DECIMAL), new TableColumn("p_comment", ValueType.TEXT)));
        tables.put("region", new TPCHTable(new TableColumn("r_regionkey", ValueType.INTEGER), new TableColumn("r_name", ValueType.TEXT), new TableColumn("r_comment", ValueType.TEXT)));
        tables.put("nation", new TPCHTable(new TableColumn("n_nationkey", ValueType.INTEGER), new TableColumn("n_name", ValueType.TEXT), new TableColumn("n_regionkey", ValueType.INTEGER), new TableColumn("n_comment", ValueType.TEXT)));
        tables.put("supplier", new TPCHTable(new TableColumn("s_suppkey", ValueType.INTEGER), new TableColumn("s_name", ValueType.TEXT), new TableColumn("s_address", ValueType.TEXT), new TableColumn("s_nationkey", ValueType.INTEGER), new TableColumn("s_phone", ValueType.TEXT), new TableColumn("s_acctbal", ValueType.DECIMAL), new TableColumn("s_comment", ValueType.TEXT)));
        tables.put("partsupp", new TPCHTable(new TableColumn("ps_partkey", ValueType.INTEGER), new TableColumn("ps_suppkey", ValueType.INTEGER), new TableColumn("ps_availqty", ValueType.INTEGER), new TableColumn("ps_supplycost", ValueType.DECIMAL), new TableColumn("ps_comment", ValueType.TEXT)));
        tables.put("customer", new TPCHTable(new TableColumn("c_custkey", ValueType.INTEGER), new TableColumn("c_name", ValueType.TEXT), new TableColumn("c_address", ValueType.TEXT), new TableColumn("c_nationkey", ValueType.INTEGER), new TableColumn("c_phone", ValueType.TEXT), new TableColumn("c_acctbal", ValueType.DECIMAL), new TableColumn("c_mktsegment", ValueType.TEXT), new TableColumn("c_comment", ValueType.TEXT)));
        tables.put("orders", new TPCHTable(new TableColumn("o_orderkey", ValueType.INTEGER), new TableColumn("o_custkey", ValueType.INTEGER), new TableColumn("o_orderstatus", ValueType.TEXT), new TableColumn("o_totalprice", ValueType.DECIMAL), new TableColumn("o_orderdate", ValueType.DATE), new TableColumn("o_orderpriority", ValueType.TEXT), new TableColumn("o_clerk", ValueType.TEXT), new TableColumn("o_shippriority", ValueType.INTEGER), new TableColumn("o_comment", ValueType.TEXT)));
        tables.put("lineitem", new TPCHTable(new TableColumn("l_orderkey", ValueType.INTEGER), new TableColumn("l_partkey", ValueType.INTEGER), new TableColumn("l_suppkey", ValueType.INTEGER), new TableColumn("l_linenumber", ValueType.INTEGER), new TableColumn("l_quantity", ValueType.DECIMAL), new TableColumn("l_extendedprice", ValueType.DECIMAL), new TableColumn("l_discount", ValueType.DECIMAL), new TableColumn("l_tax", ValueType.DECIMAL), new TableColumn("l_returnflag", ValueType.TEXT), new TableColumn("l_linestatus", ValueType.TEXT), new TableColumn("l_shipdate", ValueType.DATE), new TableColumn("l_commitdate", ValueType.DATE), new TableColumn("l_receiptdate", ValueType.DATE), new TableColumn("l_shipinstruct", ValueType.TEXT), new TableColumn("l_shipmode", ValueType.TEXT), new TableColumn("l_comment", ValueType.TEXT)));
    }

    @Override
    public Table getTable(String name) {
        return tables.get(name);
    }
}
