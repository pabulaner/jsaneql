package de.pabulaner.jsaneql.util;

public class StringView {

    /**
     * The empty string view.
     */
    public static final StringView EMPTY = new StringView("", 0, 0);

    /**
     * The original source.
     */
    private final String source;

    /**
     * The start index inside the source (inclusive).
     */
    private final int begin;

    /**
     * The end index inside the source (exclusive).
     */
    private final int end;

    public StringView(String source, int begin, int end) {
        this.source = source;
        this.begin = begin;
        this.end = end;
    }

    public String getSource() {
        return source;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringView casted) {
            return casted.toString().equals(toString());
        }

        return false;
    }

    @Override
    public String toString() {
        return source.substring(begin, end);
    }
}
