package org.fbs.jcu.data;

import org.fbs.jcu.exception.ArgsException;
import org.fbs.jcu.util.ArgsParser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class App {

    public App(String[] args, AppArguments appArguments, boolean mustContainsArgs, String funcName, String projectPackage) throws ArgsException {
        this.args = args;
        this.appArguments = appArguments;
        this.argsParser = new ArgsParser(args, appArguments, mustContainsArgs);
        this.funcName = funcName;
        this.projectPackage = projectPackage;
        doBashApp();
    }

    protected static String funcName, projectPackage;
    protected String bashApp, bashAppComplete;
    private static ArgsParser argsParser;
    private final String[] args;
    private final AppArguments appArguments;

    protected void doBashApp(){
        bashApp = String.format(
                """
                ## javaCU

                %s(){
                    # shellcheck disable=SC2088
                    java -cp '~/javacu/javacu.jar' %s "$*"
                }

                ## javaCU
                """, funcName, projectPackage);
    }
    protected void doBashAppComplete(){
        StringBuilder options = new StringBuilder();
        for (Option option: appArguments.getOptions()){
            options.append(option.getTyping());
        }
        StringBuilder keys = new StringBuilder();
        for (Key key: appArguments.getKeys()){
            keys.append(key.getTyping());
        }
        bashAppComplete = String.format(
                """
                ## javaCU

                _%s_completions() {
                    if [[ ${COMP_WORDS[COMP_CWORD]} == -* ]] ; then
                        # shellcheck disable=SC2207
                        COMPREPLY=( $(compgen -W '%s' -- ${COMP_WORDS[COMP_CWORD]}) )
                    elif [[ ${COMP_WORDS[COMP_CWORD]} == --* ]] ; then
                        # shellcheck disable=SC2207
                        COMPREPLY=( $(compgen -W '%s' -- ${COMP_WORDS[COMP_CWORD]}) )
                    elif
                    fi
                }
                complete -F _%s_completions %s
                         
                         ## javaCU
                """, funcName, options.toString(), keys.toString(), funcName, funcName);
    }

    public static void callArg(@NotNull Option option, String value){
        option.setValue(value);
    }
    public static void callArg(@NotNull Key key){
        key.call();
    }

    public String getFuncName() {
        return funcName;
    }

    public String getProjectPackage() {
        return projectPackage;
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
