package org.fbs.jcu.data;

import org.fbs.jcu.exception.ArgsException;
import org.fbs.jcu.util.ArgsParser;
import org.jetbrains.annotations.NotNull;

public class App {

    public App(String[] args, Option[] options, Key[] keys, boolean mustContainsArgs) throws ArgsException {
        this.options = options;
        this.keys = keys;
        this.args = args;
        argsParser = new ArgsParser(args, options, keys, mustContainsArgs);
    }

    private final ArgsParser argsParser;
    private final String[] args;
    private final Option[] options;
    private final Key[] keys;

    public static void callArg(@NotNull Option option, String value){
        option.setValue(value);
    }
    public static void callArg(@NotNull Key key){
        key.call();
    }

    public ArgsParser getArgsParser() {
        return argsParser;
    }

    public String[] getArgs() {
        return args;
    }

    public Option[] getOptions() {
        return options;
    }

    public Key[] getKeys() {
        return keys;
    }
}
