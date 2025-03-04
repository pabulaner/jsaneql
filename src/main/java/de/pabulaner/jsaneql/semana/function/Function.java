package de.pabulaner.jsaneql.semana.function;

import java.util.Arrays;
import java.util.List;

public class Function {

    private final Builtin builtin;

    private final String name;

    private final List<FunctionArg> args;

    public Function(Builtin builtin, String name, FunctionArg... args) {
        this.builtin = builtin;
        this.name = name;
        this.args = Arrays.asList(args);
    }

    public int getArgIndex(String name) {
        for (int i = 0; i < args.size(); i++) {
            FunctionArg arg = args.get(i);

            if (arg.getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public Builtin getBuiltin() {
        return builtin;
    }

    public String getName() {
        return name;
    }

    public List<FunctionArg> getArgs() {
        return args;
    }
}
