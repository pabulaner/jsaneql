package de.pabulaner.jsaneql.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestDatabase implements Database {

    private final List<TestTable> tables;

    public TestDatabase() {
        this.tables = new ArrayList<>();

        tables.add(new TestTable("accounts", Arrays.asList(
                new TestTableColumn("id", ValueType.INTEGER),
                new TestTableColumn("created_at", ValueType.DATE),
                new TestTableColumn("deleted", ValueType.BOOLEAN),
                new TestTableColumn("username", ValueType.STRING),
                new TestTableColumn("password", ValueType.STRING),
                new TestTableColumn("age", ValueType.INTEGER),
                new TestTableColumn("country", ValueType.STRING)
        ))
                .addRow("1", "2017-05-02", "false", "paul", "1234", "16", "germany")
                .addRow("2", "2018-07-06", "false", "peter", "what", "32", "germany")
                .addRow("3", "2005-04-08", "false", "marc", "password", "19", "hungary")
                .addRow("4", "2010-03-12", "false", "hans", "1235", "40", "hungary")
                .addRow("5", "2012-01-01", "false", "jack", "hans", "60", "austria")
        );

        tables.add(new TestTable("posts", Arrays.asList(
                new TestTableColumn("id", ValueType.INTEGER),
                new TestTableColumn("account_id", ValueType.INTEGER),
                new TestTableColumn("post_id", ValueType.INTEGER),
                new TestTableColumn("created_at", ValueType.DATE),
                new TestTableColumn("deleted", ValueType.BOOLEAN),
                new TestTableColumn("type", ValueType.STRING),
                new TestTableColumn("public", ValueType.BOOLEAN)
        ))
                .addRow("1", "1", "1", "2017-05-02", "false", "video", "true")
                .addRow("2", "1", "2", "2015-06-02", "true", "video", "true")
                .addRow("3", "3", "1", "2016-07-02", "false", "comment", "false")
                .addRow("4", "4", "2", "2018-08-02", "false", "comment", "true")
                .addRow("5", "2", "3", "2014-09-02", "false", "video", "false")
        );

        tables.add(new TestTable("videos", Arrays.asList(
                new TestTableColumn("id", ValueType.INTEGER),
                new TestTableColumn("title", ValueType.STRING),
                new TestTableColumn("content", ValueType.STRING)
        ))
                .addRow("1", "Hello World!", "a0efjkcj3a403ieal3403")
                .addRow("2", "Chuck Norris is behieafdjfaje!", "a0efjkcj3a403ieal3403")
                .addRow("3", "Nice", "a0efjkcj3a403ieal3403")
        );

        tables.add(new TestTable("comments", Arrays.asList(
                new TestTableColumn("id", ValueType.INTEGER),
                new TestTableColumn("text", ValueType.STRING)
        ))
                .addRow("1", "Nice Video!")
                .addRow("2", "Awesome...")
        );

        tables.add(new TestTable("likes", Arrays.asList(
                new TestTableColumn("account_id", ValueType.INTEGER),
                new TestTableColumn("post_id", ValueType.INTEGER),
                new TestTableColumn("created_at", ValueType.DATE),
                new TestTableColumn("deleted", ValueType.BOOLEAN)
        ))
                .addRow("2", "2", "2020-02-02", "false")
                .addRow("2", "3", "2025-01-06", "false")
                .addRow("2", "4", "2019-04-08", "true")
                .addRow("1", "4", "2022-03-12", "false")
                .addRow("4", "1", "2023-01-01", "false")
                .addRow("4", "2", "2019-03-04", "false")
        );

        tables.add(new TestTable("followers", Arrays.asList(
                new TestTableColumn("account_id", ValueType.INTEGER),
                new TestTableColumn("follower_id", ValueType.INTEGER),
                new TestTableColumn("created_at", ValueType.DATE),
                new TestTableColumn("deleted", ValueType.BOOLEAN)
        ))
                .addRow("1", "2", "2024-05-02", "false")
                .addRow("1", "3", "2024-07-06", "false")
                .addRow("1", "4", "2023-04-08", "false")
                .addRow("2", "4", "2022-03-12", "false")
                .addRow("3", "1", "2023-01-01", "false")
        );
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
        return tables.stream()
                .map(Table.class::cast)
                .collect(Collectors.toList());
    }
}
