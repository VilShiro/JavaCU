package org.fbs.jcu.data;

import org.fbs.jcu.exception.ArgsException;
import org.fbs.jcu.util.ArgsParser;
import org.jetbrains.annotations.NotNull;

public class App {

    public App(String[] args, AppArguments appArguments, boolean mustContainsArgs, String appName, String projectPackage) throws ArgsException {
        this.args = args;
        this.appArguments = appArguments;
        this.argsParser = new ArgsParser(args, appArguments, mustContainsArgs);
        this.appName = appName;
        this.projectPackage = projectPackage;
        doBashApp();
    }

    protected static String appName, projectPackage;
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
                    java -cp '~/javacu/javacu.jar' %s $*
                }

                ## javaCU
                """, appName, projectPackage);
    }
    protected void doBashAppComplete(){
        StringBuilder options = new StringBuilder();
        for (Option option: appArguments.getOptions()){
            options.append(option.getTyping()).append(" ").append(option.getAlias()).append(" ");
        }
        StringBuilder keys = new StringBuilder();
        for (Key key: appArguments.getKeys()){
            keys.append(key.getTyping()).append(" ").append(key.getAlias()).append(" ");
        }
        StringBuilder functions = new StringBuilder();
        for (Function function: appArguments.getFunctions()){
            functions.append(function.getTyping()).append(" ");
        }
        bashAppComplete = String.format(
                """
                ## javaCU

                _%s_completions () {
                    local cur prev
                    cur="${COMP_WORDS[COMP_CWORD]}"
                    prev="${COMP_WORDS[COMP_CWORD-1]}"
                
                    local options="%s"
                    local keys="%s"
                    local functions="%s"
                
                    if [[ $cur == -* ]]; then
                      if [[ $cur == --* ]]; then
                        COMPREPLY=($(compgen -W "${keys}" -- ${cur}))
                      else
                        COMPREPLY=($(compgen -W "${options}" -- ${cur}))
                      fi
                    elif [[ $COMP_CWORD -eq 1 || -z $prev ]]; then
                      COMPREPLY=($(compgen -W "${functions}" -- ${cur}))
                    else
                      COMPREPLY=()
                    fi
                }
                  
                complete -F _%s_completions %s
                         
                ## javaCU
                """, appName, options.toString(), keys.toString(), functions.toString(), appName, appName);
    }

    public static @NotNull String getOptionsAsString(@NotNull AppArguments appArguments){
        StringBuilder options = new StringBuilder();
        for (Option option: appArguments.getOptions()){
            options.append(option.toString()).append("\n");
        }
        return options.toString();
    }

    public static @NotNull String getKeysAsString(@NotNull AppArguments appArguments){
        StringBuilder keys = new StringBuilder();
        for (Key key: appArguments.getKeys()){
            keys.append(key.toString()).append("\n");
        }
        return keys.toString();
    }

    public static @NotNull String getFunctionsAsString(@NotNull AppArguments appArguments){
        StringBuilder functions = new StringBuilder();
        for (Function function: appArguments.getFunctions()){
            functions.append(function.toString()).append("\n");
        }
        return functions.toString();
    }

    public static void callArg(@NotNull Option option, String value){
        option.setValue(value);
    }

    public static void callArg(@NotNull Key key){
        key.call();
    }

    public String getFuncName() {
        return appName;
    }

    public String getProjectPackage() {
        return projectPackage;
    }

    public ArgsParser getParsed() {
        return argsParser;
    }

    public String[] getArgs() {
        return args;
    }

    public AppArguments getAppArguments(){
        return appArguments;
    }

    public void run() throws Exception {}

}
