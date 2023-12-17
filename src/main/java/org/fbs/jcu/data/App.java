package org.fbs.jcu.data;

import org.fbs.jcu.exception.ArgsException;
import org.fbs.jcu.util.ArgsParser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class App {

    public App(String[] args, AppArguments appArguments, boolean mustContainsArgs) throws ArgsException {
        this.args = args;
        this.appArguments = appArguments;
        argsParser = new ArgsParser(args, appArguments, mustContainsArgs);
    }

    private static ArgsParser argsParser;
    private final String[] args;
    private final AppArguments appArguments;

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

    public AppArguments getAppArguments(){
        return appArguments;
    }

    public void run() throws IOException, InterruptedException {}

}
