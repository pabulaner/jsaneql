package de.pabulaner.jsaneql.exec;

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

    public void writeIU(IU iu) {
        String name = names.computeIfAbsent(iu, key -> "v" + (names.size() + 1));
        write(name);
    }

    public void writeType(ValueType type) {
        switch (type) {
            case TEXT: write("text"); break;
            case INTEGER: write("integer"); break;
            case DECIMAL: write("float"); break;
            case BOOLEAN: write("boolean"); break;
            case DATE: write("date"); break;
            case INTERVAL: write("interval"); break;
            case NULL: write("unknown"); break;
        }
    }

    public String getResult() {
        return result.toString();
    }
}
