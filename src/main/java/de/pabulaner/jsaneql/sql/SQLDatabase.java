package de.pabulaner.jsaneql.sql;

import de.pabulaner.jsaneql.algebra.IUColumn;
import de.pabulaner.jsaneql.compile.CompileException;
import de.pabulaner.jsaneql.compile.Compiler;
import de.pabulaner.jsaneql.compile.SQLWriter;
import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.Column;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.semana.result.ExpressionResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class SQLDatabase implements Database {

    private final Connection connection;

    private final Map<String, SQLTable> tables;

    public SQLDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:;INIT=CREATE SCHEMA database\\;SET SCHEMA database;");
        tables = new HashMap<>();
    }

    public SQLResult execSane(String query) throws CompileException, SQLException {
        Compiler compiler = new Compiler(this);
        ExpressionResult result = compiler.compile(query);
        SQLWriter sql = new SQLWriter();

        if (result.isScalar()) {
            sql.write("SELECT ");
            result.scalar().generate(sql);
        } else {
            result.table().generate(sql);
        }

        try (Statement stmt = connection.createStatement()) {
            Queue<Value[]> rows = new LinkedList<>();
            ResultSet set = stmt.executeQuery(sql.toString());

            while (set.next()) {
                Value[] row = new Value[result.binding().getColumns().size()];
                
                for (int i = 0; i < row.length; i++) {
                    int columnIndex = i + 1;
                    IUColumn column = result.binding().getColumns().get(i);

                    switch (column.getIU().getType()) {
                        case TEXT: row[i] = Value.ofString(set.getString(columnIndex)); break;
                        case INTEGER: row[i] = Value.ofInteger(set.getLong(columnIndex)); break;
                        case DECIMAL: row[i] = Value.ofDecimal(set.getDouble(columnIndex)); break;
                        case BOOLEAN: row[i] = Value.ofBoolean(set.getBoolean(columnIndex)); break;
                        case DATE: row[i] = Value.ofDate(set.getDate(columnIndex).toLocalDate().atStartOfDay()); break;
                        case INTERVAL: row[i] = Value.ofInterval(null); break;
                        case UNKNOWN: row[i] = Value.ofNull(); break;
                    }
                }

                rows.add(row);
            }

            return new SQLResult(result.binding().getColumns()
                    .stream()
                    .map(column -> new Column(column.getName(), column.getIU().getType()))
                    .collect(Collectors.toList()), rows);
        }
    }

    public SQLTable addTable(String name, Column... columns) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            StringBuilder command = new StringBuilder("CREATE TABLE " + name + " (");
            boolean first = true;

            for (Column column : columns) {
                if (first) {
                    first = false;
                } else {
                    command.append(", ");
                }

                command.append(column.getName())
                        .append(" ")
                        .append(column.getType().getName());
            }

            command.append(");");
            stmt.execute(command.toString());
        }

        SQLTable table = new SQLTable(connection, name, Arrays.asList(columns));
        tables.put(name, table);

        return table;
    }

    @Override
    public Table getTable(String name) {
        return tables.get(name);
    }
}
