package de.pabulaner.jsaneql.semana;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.algebra.expression.BetweenExpression;
import de.pabulaner.jsaneql.algebra.expression.BinaryExpression;
import de.pabulaner.jsaneql.algebra.expression.CastExpression;
import de.pabulaner.jsaneql.algebra.operator.JoinOperator;
import de.pabulaner.jsaneql.algebra.operator.MapOperator;
import de.pabulaner.jsaneql.algebra.operator.OrderByOperator;
import de.pabulaner.jsaneql.parser.ast.AccessNode;
import de.pabulaner.jsaneql.parser.ast.BinaryNode;
import de.pabulaner.jsaneql.parser.ast.CallNode;
import de.pabulaner.jsaneql.parser.ast.ArgNode;
import de.pabulaner.jsaneql.parser.ast.CastNode;
import de.pabulaner.jsaneql.parser.ast.ListNode;
import de.pabulaner.jsaneql.parser.ast.Node;
import de.pabulaner.jsaneql.parser.ast.QueryNode;
import de.pabulaner.jsaneql.parser.ast.TokenNode;
import de.pabulaner.jsaneql.parser.ast.UnaryNode;
import de.pabulaner.jsaneql.schema.Database;
import de.pabulaner.jsaneql.schema.Table;
import de.pabulaner.jsaneql.schema.TableColumn;
import de.pabulaner.jsaneql.schema.ValueType;
import de.pabulaner.jsaneql.algebra.expression.ConstExpression;
import de.pabulaner.jsaneql.algebra.expression.RefExpression;
import de.pabulaner.jsaneql.algebra.expression.UnaryExpression;
import de.pabulaner.jsaneql.semana.binding.Scope;
import de.pabulaner.jsaneql.semana.function.ExpressionArg;
import de.pabulaner.jsaneql.semana.function.Function;
import de.pabulaner.jsaneql.semana.function.FunctionArg;
import de.pabulaner.jsaneql.semana.function.Functions;
import de.pabulaner.jsaneql.algebra.operator.FilterOperator;
import de.pabulaner.jsaneql.algebra.operator.ScanOperator;
import de.pabulaner.jsaneql.semana.result.OrderingInfo;
import de.pabulaner.jsaneql.semana.result.Result;
import de.pabulaner.jsaneql.semana.binding.Binding;
import de.pabulaner.jsaneql.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SemanticAnalyzer {

    private final Database db;

    private final Functions functions;

    public SemanticAnalyzer(Database db) {
        this.db = db;
        this.functions = new Functions();
    }

    public Result parse(QueryNode ast) {
        return parseExpression(new Binding(), ast.getBody());
    }

    private Result parseExpression(Binding binding, Node ast) {
        switch (ast.getType()) {
            case TOKEN: return parseToken(binding, (TokenNode) ast);
            case ACCESS: return parseAccess(binding, (AccessNode) ast);
            case CALL: return parseCall(binding, (CallNode) ast);
            case UNARY_EXPRESSION: return parseUnaryExpression(binding, (UnaryNode) ast);
            case BINARY_EXPRESSION: return parseBinaryExpression(binding, (BinaryNode) ast);
            case CAST: return parseCast(binding, (CastNode) ast);
            default: throw new InvalidAstException();
        }
    }

    private Result parseToken(Binding binding, TokenNode ast) {
        Token token = ast.getToken();

        if (token.getKind() == Token.Kind.IDENT) {
            String name = extractValue(ast);
            IU iu = binding.lookup(name);

            // Is it a column?
            if (iu != null) {
                if (iu == Binding.AMBIGUOUS_IU) {
                    reportError("'" + name + "' is ambiguous");
                }

                return new Result(new RefExpression(iu));
            }

            // Is it a scan?
            Table table = db.getTable(name);

            if (table == null) {
                reportError("unknown table '" + name + "'");
            }

            Scope scope = binding.addScope(name);

            for (TableColumn column : table.getColumns()) {
                binding.addBinding(scope, column.getName(), new IU(column.getType()));
            }

            return new Result(new ScanOperator(table), binding);
        } else {
            ValueType type;

            switch (token.getKind()) {
                case STRING: type = ValueType.STRING; break;
                case INTEGER: type = ValueType.INTEGER; break;
                case DECIMAL: type = ValueType.DECIMAL; break;
                case BOOLEAN: type = ValueType.BOOLEAN; break;
                case NULL: type = ValueType.NULL; break;
                default: throw new InvalidAstException();
            }

            return new Result(new ConstExpression(type, token.getValue().toString()));
        }
    }

    private Result parseAccess(Binding binding, AccessNode ast) {
        String base = extractValue(ast.getBase());
        String name = extractValue(ast.getPart());

        if (base == null) {
            reportError("invalid access to column '" + name + "'");
        }

        IU iu = binding.lookup(base, name);

        if (iu == null) {
            reportError("'" + base + "." + name + "' not found");
        }

        if (iu == Binding.AMBIGUOUS_SCOPE) {
            reportError("'" + base + "' is ambiguous");
        }

        return new Result(new RefExpression(iu));
    }

    private Result parseCall(Binding binding, CallNode ast) {
        // Recognize call and lookup function signature
        Result base = null;
        String name;
        Function function;

        if (ast.getFunc().getType() == Node.Type.ACCESS) {
            AccessNode access = (AccessNode) ast.getFunc();
            String type;

            base = parseExpression(binding, access.getBase());
            name = extractValue(access.getPart());

            if (base.isScalar()) {
                function = functions.getFunction(base.scalar().getType(), name);
                type = base.scalar().getType().name();
            } else {
                function = functions.getTable(name);
                type = "TABLE";
            }

            if (function == null) {
                reportError("function '" + name + "' not found for '" + type + "'");
            }
        } else {
            if (ast.getFunc().getType() != Node.Type.TOKEN) {
                reportError("invalid function name");
            }

            name = extractValue(ast.getFunc());
            function = functions.getFunction(name);

            if (function == null) {
                reportError("function '" + name + "' not found");
            }
        }

        // Assign arguments to positions
        List<ArgNode> args = new ArrayList<>();
        boolean named = false;

        for (ArgNode arg : typedList(ast.getArgs())) {
            // Check for named arguments
            if (arg.getName() != null) {
                named = true;

                while (args.size() < function.getArgs().size()) {
                    args.add(null);
                }

                String argName = extractValue(arg.getName());
                int index = function.getArgIndex(argName);

                if (index == -1) {
                    reportError("parameter '" + argName + "' not found in call to '" + name + "'");
                }

                if (args.get(index) != null) {
                    reportError("parameter '" + argName + "' provided more than once");
                }

                args.set(index, arg);
            } else {
                if (named) {
                    reportError("positional parameters cannot be used after named parameters in call to '" + name + "'");
                }

                if (args.size() >= function.getArgs().size()) {
                    reportError("too many parameters in call to '" + name + "'");
                }

                args.add(arg);
            }
        }

        while (args.size() < function.getArgs().size()) {
            args.add(null);
        }

        for (int i = 0; i < function.getArgs().size(); i++) {
            FunctionArg arg = function.getArgs().get(i);

            if (args.get(i) == null && !arg.isOptional()) {
                reportError("parameter '" + arg.getName() + "' missing in call to '" + name + "'");
            }
        }

        switch (function.getBuiltin()) {
            case ASC: return parseOrdering(base, false);
            case DESC: return parseOrdering(base, true);
            case BETWEEN: return parseBetween(binding, base, args);
            case FILTER: return parseFilter(binding, base, args);
            case JOIN: return parseJoin(binding, base, args);
            case GROUP_BY:
            case ORDER_BY: return parseOrderBy(binding, base, args);
            case MAP: return parseMap(binding, base, args, false);
            case PROJECT: return parseMap(binding, base, args, true);
            case AS: return parseAs(binding, base, args);
        }

        reportError("call not implemented yet");
        return null;
    }

    private Result parseOrdering(Result base, boolean descending) {
        base.ordering().setDescending(descending);
        return base;
    }

    private Result parseBetween(Binding binding, Result base, List<ArgNode> args) {
        Result lower = scalarArgument("between", "lower", binding, args.get(0));
        Result upper = scalarArgument("between", "upper", binding, args.get(1));

        enforceComparable(base, lower);
        enforceComparable(base, upper);

        return new Result(new BetweenExpression(base.scalar(), lower.scalar(), upper.scalar()));
    }

    private Result parseFilter(Binding binding, Result base, List<ArgNode> args) {
        Result condition = scalarArgument("filter", "condition", binding, args.get(0));

        if (condition.scalar().getType() != ValueType.BOOLEAN) {
            reportError("'filter' requires a boolean condition");
        }

        return new Result(new FilterOperator(base.table(), condition.scalar()), binding);
    }

    private Result parseJoin(Binding binding, Result base, List<ArgNode> args) {
        JoinOperator.Type type = JoinOperator.Type.INNER;
        boolean leftOnly = false;
        boolean rightOnly = false;

        if (args.get(2) != null) {
            String symbol = symbolArgument("join", "type", args.get(2));

            switch (symbol) {
                case "inner": break;
                case "left":
                case "leftouter": type = JoinOperator.Type.LEFT_OUTER; break;
                case "right":
                case "rightouter": type = JoinOperator.Type.RIGHT_OUTER; break;
                case "full":
                case "fullouter": type = JoinOperator.Type.FULL_OUTER; break;
                case "leftsemi":
                case "exists": type = JoinOperator.Type.LEFT_SEMI; leftOnly = true; break;
                case "rightsemi": type = JoinOperator.Type.RIGHT_SEMI; rightOnly = true; break;
                case "leftanti":
                case "notexists": type = JoinOperator.Type.LEFT_ANTI; leftOnly = true; break;
                case "rightanti": type = JoinOperator.Type.RIGHT_ANTI; rightOnly = true; break;
                default: reportError("unknown join type '" + symbol + "'");
            }
        }

        Result table = tableArgument("join", "table", new Binding(), args.get(0));
        Binding resultBinding = new Binding(binding);
        binding.join(table.binding());

        Result condition = scalarArgument("join", "on", resultBinding, args.get(1));

        if (condition.scalar().getType() != ValueType.BOOLEAN) {
            reportError("'join' requires a boolean condition");
        }

        if (leftOnly) {
            resultBinding = binding;
        }

        if (rightOnly) {
            resultBinding = table.binding();
        }

        return new Result(new JoinOperator(type, base.table(), table.table(), condition.scalar()), resultBinding);
    }

    private Result parseOrderBy(Binding binding, Result base, List<ArgNode> args) {
        List<OrderByOperator.Entry> entries = new ArrayList<>();

        if (args.get(0) != null) {
            List<ExpressionArg> list = listArgument("orderby", "expressions", binding, args.get(0));

            for (ExpressionArg arg : list) {
                if (!arg.getResult().isScalar()) {
                    reportError("orderby requires scalar values");
                }

                OrderingInfo ordering = arg.getResult().ordering();
                entries.add(new OrderByOperator.Entry(arg.getResult().scalar(), ordering.isDescending()));
            }
        }

        BiFunction<String, ArgNode, Long> handleConstant = (name, arg) -> {
            String message = "'" + name + "' requires integer constant";

            if (arg.getValue().getType() != Node.Type.TOKEN) {
                reportError(message);
            }

            TokenNode tokenNode = (TokenNode) arg.getValue();

            if (tokenNode.getToken().getKind() != Token.Kind.INTEGER) {
                reportError(message);
            }

            return Long.valueOf(tokenNode.getView().toString());
        };

        long limit = args.get(1) != null ? handleConstant.apply("limit", args.get(1)) : 0;
        long offset = args.get(2) != null ? handleConstant.apply("offset", args.get(2)) : 0;

        return new Result(new OrderByOperator(base.table(), entries, limit, offset), binding);
    }

    private Result parseMap(Binding binding, Result base, List<ArgNode> args, boolean project) {
        String name = project ? "project" : "map";
        List<ExpressionArg> list = listArgument(name, "expressions", binding, args.get(0));
        List<MapOperator.Entry> entries = new ArrayList<>();

        // compute the expressions
        for (ExpressionArg arg : list) {
            if (!arg.getResult().isScalar()) {
                reportError(name + " requires scalar values");
            }

            ValueType type = arg.getResult().scalar().getType();
            entries.add(new MapOperator.Entry(new IU(type), arg.getResult().scalar()));
        }

        // make expressions visible
        Binding resultBinding = project ? new Binding() : binding;
        int index = 0;

        for (ExpressionArg arg : list) {
            String argName = arg.getName();

            if (argName.isEmpty()) {
                argName = String.valueOf(resultBinding.getColumnLookup().size() + 1);
            }

            resultBinding.addBinding(argName, entries.get(index++).getIU());
        }

        return new Result(new MapOperator(base.table(), entries), resultBinding);
    }

    private Result parseAs(Binding binding, Result base, List<ArgNode> args) {
        String name = symbolArgument("as", "name", args.get(0));
        base.binding().getScopes().clear();
        base.binding().getScopes()
                .computeIfAbsent(name, key -> new Scope())
                .getColumns()
                .putAll(base.binding().getColumnLookup());

        return base;
    }

    private Result parseUnaryExpression(Binding binding, UnaryNode ast) {
        Result value = parseExpression(binding, ast.getValue());

        if (!value.isScalar()) {
            reportError("expected scalar value");
        }

        if ((ast.getOperation() == UnaryExpression.Operation.ADD || ast.getOperation() == UnaryExpression.Operation.SUB) && !isNumeric(value.scalar().getType())) {
            reportError("expected numeric value");
        }

        if (ast.getOperation() == UnaryExpression.Operation.NOT && value.scalar().getType() != ValueType.BOOLEAN) {
            reportError("expected boolean value");
        }

        return new Result(new UnaryExpression(ast.getOperation(), value.scalar()));
    }

    private Result parseBinaryExpression(Binding binding, BinaryNode ast) {
        ValueType[] resultType = { null };

        Result left = parseExpression(binding, ast.getLeft());
        Result right = parseExpression(binding, ast.getRight());

        if (!left.isScalar() || !right.isScalar()) {
            reportError("expected scalar values");
        }

        Runnable doArithmetic = () -> {
            if (isNumeric(left.scalar().getType()) && isNumeric(right.scalar().getType())) {
                resultType[0] = left.scalar().getType() == ValueType.DECIMAL || right.scalar().getType() == ValueType.DECIMAL
                        ? ValueType.DECIMAL
                        : ValueType.INTEGER;
            } else if (ast.getOperation() == BinaryExpression.Operation.ADD && left.scalar().getType() == ValueType.STRING && right.scalar().getType() == ValueType.STRING) {
                resultType[0] = ValueType.STRING;
            } else if ((ast.getOperation() == BinaryExpression.Operation.ADD || ast.getOperation() == BinaryExpression.Operation.SUB) && left.scalar().getType() == ValueType.DATE && right.scalar().getType() == ValueType.INTERVAL) {
                resultType[0] = ValueType.DATE;
            } else {
                reportError("invalid arithmetic, types are not compatible");
            }
        };

        Runnable doComparison = () -> {
            enforceComparable(left, right);
            resultType[0] = ValueType.BOOLEAN;
        };

        Runnable doLogic = () -> {
            if (left.scalar().getType() != ValueType.BOOLEAN || right.scalar().getType() != ValueType.BOOLEAN) {
                reportError("expected boolean values");
            }

            resultType[0] = ValueType.BOOLEAN;
        };

        switch (ast.getOperation()) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case MOD:
            case POW: doArithmetic.run(); break;
            case EQ:
            case NEQ:
            case LT:
            case GT:
            case LE:
            case GE: doComparison.run(); break;
            case AND:
            case OR: doLogic.run(); break;
        }

        return new Result(new BinaryExpression(resultType[0], ast.getOperation(), left.scalar(), right.scalar()));
    }

    private Result parseCast(Binding binding, CastNode ast) {
        Result value = parseExpression(binding, ast.getValue());
        String cast = extractValue(ast.getCast());
        ValueType type = null;

        if (value == null) {
            reportError("expected scalar value");
        }

        if (cast == null) {
            reportError("expected type");
        }

        switch (cast) {
            case "string": type = ValueType.STRING; break;
            case "integer": type = ValueType.INTEGER; break;
            case "decimal": type = ValueType.DECIMAL; break;
            case "boolean": type = ValueType.BOOLEAN; break;
            case "date": type = ValueType.DATE; break;
            case "interval": type = ValueType.INTERVAL; break;
            default: reportError("unknown type '" + cast + "'");
        }

        return new Result(new CastExpression(value.scalar(), type));
    }

    private String symbolArgument(String funcName, String argName, ArgNode arg) {
        if (arg.isList() || arg.getValue().getType() != Node.Type.TOKEN) {
            reportError("parameter '" + argName + "' requires a symbol in call to '" + funcName + "'");
        }

        return extractValue(arg.getValue());
    }

    private Result scalarArgument(String funcName, String argName, Binding binding, ArgNode arg) {
        if (arg.isList()) {
            reportError("parameter '" + argName + "' requires a scalar in call to '" + funcName + "'");
        }

        Result scalar = parseExpression(binding, arg.getValue());

        if (!scalar.isScalar()) {
            reportError("parameter '" + argName + "' requires a scalar in call to '" + funcName + "'");
        }

        return scalar;
    }

    private Result tableArgument(String funcName, String argName, Binding binding, ArgNode arg) {
        if (arg.isList()) {
            reportError("parameter '" + argName + "' requires a table in call to '" + funcName + "'");
        }

        Result table = parseExpression(binding, arg.getValue());

        if (!table.isTable()) {
            reportError("parameter '" + argName + "' requires a table in call to '" + funcName + "'");
        }

        return table;
    }

    private List<ExpressionArg> listArgument(String funcName, String argName, Binding binding, ArgNode arg) {
        List<ExpressionArg> result = new ArrayList<>();

        if (arg.isList()) {
            ListNode list = (ListNode) arg.getValue();

            for (ArgNode subArg : typedList(list.getValues())) {
                Result value = parseExpression(binding, subArg.getValue());
                String name = subArg.getName() != null
                    ? extractValue(subArg.getName())
                    : inferName(subArg.getValue());

                result.add(new ExpressionArg(name, value));
            }
        } else {
            Result value = parseExpression(binding, arg.getValue());
            String name = inferName(arg.getValue());

            result.add(new ExpressionArg(name, value));
        }

        return result;
    }

    private void enforceComparable(Result left, Result right) {
        boolean comparable = false;

        switch (left.scalar().getType()) {
            case STRING: comparable = right.scalar().getType() == ValueType.STRING; break;
            case INTEGER:
            case DECIMAL: comparable = right.scalar().getType() == ValueType.INTEGER || right.scalar().getType() == ValueType.DECIMAL; break;
            case BOOLEAN: comparable = right.scalar().getType() == ValueType.BOOLEAN; break;
            case DATE: comparable = right.scalar().getType() == ValueType.DATE; break;
            case INTERVAL: comparable = right.scalar().getType() == ValueType.INTERVAL; break;
        }

        comparable |= left.scalar().getType() == ValueType.NULL || right.scalar().getType() == null;

        if (!comparable) {
            reportError("invalid comparison, types are not compatible");
        }
    }

    private String extractValue(Node node) {
        if (node.getType() == Node.Type.TOKEN) {
            return ((TokenNode) node).getToken().getValue().toString();
        }

        return null;
    }

    private String inferName(Node node) {
        String name = extractValue(node);

        if (name == null && node.getType() == Node.Type.ACCESS) {
            name = extractValue(((AccessNode) node).getPart());
        }

        return name;
    }

    private  List<ArgNode> typedList(List<Node> list) {
        return list.stream()
                .map(ArgNode.class::cast)
                .collect(Collectors.toList());
    }

    private boolean isNumeric(ValueType type) {
        return type == ValueType.INTEGER || type == ValueType.DECIMAL;
    }

    private void reportError(String message) {
        throw new RuntimeException(message);
    }
}
