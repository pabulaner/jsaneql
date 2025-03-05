package de.pabulaner.jsaneql.semana.function;

public enum Builtin {

    // scalar
    ASC,
    DESC,
    BETWEEN,
    IN,

    // text
    LIKE,
    SUBSTR,

    // date
    EXTRACT,

    // table
    FILTER,
    JOIN,
    GROUP_BY,
    ORDER_BY,
    MAP,
    PROJECT,
    AS,

    // free
    COUNT,
    SUM,
    AVG,
    MIN,
    MAX,
}
