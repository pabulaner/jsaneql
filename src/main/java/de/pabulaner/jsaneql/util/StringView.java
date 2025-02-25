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

    /**
     * The constructor.
     */
    public StringView(String source, int begin, int end) {
        this.source = source;
        this.begin = begin;
        this.end = end;
    }

    /**
     * Returns the combined string view if both have the
     * same source. Otherwise, an exception is thrown.
     */
    public StringView combine(StringView other) {
        if (other.source == null && source == null) {
            return this;
        }

        if (other.source == null || source == null || !other.source.equals(source)) {
            throw new IllegalArgumentException("String views don't have the same source");
        }

        return new StringView(source, Math.min(begin, other.begin), Math.max(end, other.end));
    }

    /**
     * Returns the source of the string view.
     */
    public String getSource() {
        return source;
    }

    /**
     * Returns the beginning index of the string view.
     */
    public int getBegin() {
        return begin;
    }

    /**
     * Returns the ending index of the string view.
     */
    public int getEnd() {
        return end;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass().equals(getClass())) {
            StringView casted = (StringView) obj;
            return casted.toString().equals(toString());
        }

        return false;
    }

    @Override
    public String toString() {
        return source.substring(begin, end);
    }
}
