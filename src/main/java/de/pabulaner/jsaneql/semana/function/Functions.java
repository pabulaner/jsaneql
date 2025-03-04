package de.pabulaner.jsaneql.semana.function;

import de.pabulaner.jsaneql.schema.ValueType;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    private final List<Function> scalar;

    private final List<Function> text;

    private final List<Function> date;

    private final List<Function> table;

    private final List<Function> free;

    public Functions() {
        scalar = new ArrayList<>();
        text = new ArrayList<>();
        date = new ArrayList<>();
        table = new ArrayList<>();
        free = new ArrayList<>();

        scalar.add(new Function(Builtin.ASC, "asc"));
        scalar.add(new Function(Builtin.DESC, "desc"));
        scalar.add(new Function(Builtin.BETWEEN, "between", new FunctionArg("lower", FunctionArg.Type.EXPRESSION), new FunctionArg("upper", FunctionArg.Type.EXPRESSION)));

        table.add(new Function(Builtin.FILTER, "filter", new FunctionArg("condition", FunctionArg.Type.EXPRESSION)));
        table.add(new Function(Builtin.JOIN, "join", new FunctionArg("table", FunctionArg.Type.TABLE), new FunctionArg("on", FunctionArg.Type.EXPRESSION), new FunctionArg("type", FunctionArg.Type.SYMBOL, true)));
        table.add(new Function(Builtin.GROUP_BY, "groupby", new FunctionArg("groups", FunctionArg.Type.EXPRESSION_LIST), new FunctionArg("aggregates", FunctionArg.Type.EXPRESSION_LIST, true)));
        table.add(new Function(Builtin.ORDER_BY, "orderby", new FunctionArg("expressions", FunctionArg.Type.EXPRESSION_LIST, true), new FunctionArg("limit", FunctionArg.Type.EXPRESSION, true), new FunctionArg("offset", FunctionArg.Type.EXPRESSION, true)));
        table.add(new Function(Builtin.MAP, "map", new FunctionArg("expressions", FunctionArg.Type.EXPRESSION_LIST)));
        table.add(new Function(Builtin.PROJECT, "project", new FunctionArg("expressions", FunctionArg.Type.EXPRESSION_LIST)));
        table.add(new Function(Builtin.AS, "as", new FunctionArg("name", FunctionArg.Type.SYMBOL)));
    }

    public Function getFunction(String name) {
        return getFunction(null, name);
    }

    public Function getFunction(ValueType type, String name) {
        List<Function> functions = null;

        if (type == null) {
            functions = free;
        } else {
            switch (type) {
                case STRING: functions = text; break;
                case INTEGER:
                case DECIMAL:
                case BOOLEAN: functions = scalar; break;
                case DATE: functions = date; break;
            }
        }

        if (functions == null) {
            throw new IllegalArgumentException("Invalid type");
        }

        return functions.stream()
                .filter(function -> function.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Function getTable(String name) {
        return table.stream()
                .filter(function -> function.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
