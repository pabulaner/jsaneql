package de.pabulaner.jsaneql.compile;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.HashMap;
import java.util.Map;

public class SQLWriter {

    private final StringBuilder result;

    private final Map<IU, String> names;

    public SQLWriter() {
        result = new StringBuilder();
        names = new HashMap<>();
    }

    public void write(String sql) {
        result.append(sql);
    }

    public void writeString(String sql) {
        result.append('\'').append(sql).append('\'');
    }

    public void writeIU(IU iu) {
        String name = names.computeIfAbsent(iu, key -> "v" + (names.size() + 1));
        write(name);
    }

    public void writeType(ValueType type) {
        if (type == ValueType.DECIMAL) {
            write("float");
        } else {
            write(type.getName());
        }
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
