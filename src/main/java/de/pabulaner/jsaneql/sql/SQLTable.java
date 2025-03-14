package de.pabulaner.jsaneql.sql;

import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.Column;
import de.pabulaner.jsaneql.schema.Value;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLTable implements Table {

    private final Connection connection;

    private final String name;

    private final List<Column> columns;

    public SQLTable(Connection connection, String name, List<Column> columns) {
        this.connection = connection;
        this.name = name;
        this.columns = columns;
    }

    public void addRow(Value... values) throws SQLException {
        if (values.length != columns.size()) {
            throw new IllegalArgumentException();
        }

        try (Statement stmt = connection.createStatement()) {
            StringBuilder command = new StringBuilder("INSERT INTO " + name + " VALUES (");
            boolean first = true;

            for (int i = 0; i < columns.size(); i++) {
                if (first) {
                    first = false;
                } else {
                    command.append(", ");
                }

                if (values[i].getType() != columns.get(i).getType()) {
                    throw new IllegalArgumentException();
                }

                command.append("CAST('")
                        .append(values[i])
                        .append("' AS ")
                        .append(columns.get(i).getType().getName())
                        .append(")");
            }

            command.append(")");
            stmt.execute(command.toString());
        }
    }

    @Override
    public List<Column> getColumns() {
        return columns;
    }
}
