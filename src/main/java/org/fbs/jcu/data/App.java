package org.fbs.jcu.data;

import org.fbs.jcu.exception.ArgsException;
import org.fbs.jcu.util.ArgsParser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class App {

    public App(String[] args, AppArguments appArguments, boolean mustContainsArgs, boolean canContainSingleArgs, String appName, String projectPackage) throws ArgsException {
        this.args = args;
        this.appArguments = appArguments;
        this.appArguments.addToFunctions(new Function("help", "Get full info about options, keys and functions") {
            @Override
            public void call() {
                System.out.printf(
                        """
                        There is an options, keys and functions for "%s
                        """, appName + "\""+
                                "\n\nOptions(required has \"*\", else standard value in \"()\"):\n" + getOptionsAsString(appArguments.getOptions()) +
                                "\nKeys:\n" + getKeysAsString(appArguments.getKeys()) +
                                "\nFunctions:\n" + getFunctionsAsString(appArguments.getFunctions())
                );
            }
        });
        this.argsParser = new ArgsParser(args, this.appArguments, mustContainsArgs, canContainSingleArgs);
        this.appName = appName;
        this.projectPackage = projectPackage;
        doBashApp();
        doBashAppComplete();
    }

    protected static String appName, projectPackage;
    protected static String bashApp, bashAppComplete;
    private static ArgsParser argsParser;
    private final String[] args;
    private final AppArguments appArguments;

    protected void doBashApp(){
        bashApp = String.format(
            """
            ## %s

            %s(){
                # shellcheck disable=SC2088
                java -cp '~/javacu/javacu.jar' %s $*
            }
            """, appName, appName, projectPackage);
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
        boolean isFirstSkip = false;
        for (Function function: appArguments.getFunctions()){
            if (!isFirstSkip){
                isFirstSkip = true;
                continue;
            }
            functions.append(function.getTyping()).append(" ");
        }

        StringBuilder functionOptions = new StringBuilder();
        StringBuilder functionKeys = new StringBuilder();
        StringBuilder functionsOptionsKeys = new StringBuilder();
        StringBuilder helpKeys = new StringBuilder();
        StringBuilder helpOptions = new StringBuilder();
        StringBuilder appKeys = new StringBuilder();
        StringBuilder appOptions = new StringBuilder();

        for (Option option: appArguments.getOptions()){
            appOptions.append(option.getTyping()).append(" ");
        }
        for (Key key: appArguments.getKeys()){
            appKeys.append(key.getTyping()).append(" ");
        }

        bashAppComplete = String.format(
            """
            _%s_opts="%s"
            _%s_keys="%s"
            _help_opts="%s"
            _help_keys="%s"
            %s
              
            _%s_completions() {
                local cur prev opts
                COMPREPLY=()
                cur="${COMP_WORDS[COMP_CWORD]}"
                prev="${COMP_WORDS[1]}"
                if [[ ${cur} == --* ]] ; then
                    opts="${_%s_keys}"
                    if [[ ${prev} == "help" ]] ; then
                        opts="${opts} ${_help_keys}"
                    %s
                    fi
                elif [[ ${cur} == -* ]] ; then
                    opts="${_%s_opts}"
                    if [[ ${prev} == "help" ]] ; then
                        opts="${opts} ${_help_opts}"
                    %s
                    fi
                elif [[ $COMP_CWORD -eq 1 || -z $prev ]]; then
                    opts="help %s"
                fi
                COMPREPLY=( $(compgen -W "${opts}" -- ${cur}) )
                return 0
            }
              
            complete -F _%s_completions %s
                     
            ## %s
            """, appName, appOptions.toString(), appName,  appKeys.toString(), helpOptions.toString(), helpKeys.toString(), functionsOptionsKeys.toString(), appName, appName, functionKeys.toString(), appName, functionOptions.toString(), functions.toString(), appName, appName, appName);
    }

    public static String getBashApp() {
        return bashApp;
    }

    public static String getBashAppComplete() {
        return bashAppComplete;
    }

    public static @NotNull String getOptionsAsString(@NotNull List<Option> optionList){
        StringBuilder options = new StringBuilder();
        for (Option option: optionList){
            options.append(option.toString()).append("\n");
        }
        return options.toString();
    }

    public static @NotNull String getKeysAsString(@NotNull List<Key> keyList){
        StringBuilder keys = new StringBuilder();
        for (Key key: keyList){
            keys.append(key.toString()).append("\n");
        }
        return keys.toString();
    }

    public static @NotNull String getFunctionsAsString(@NotNull List<Function> functionList){
        StringBuilder functions = new StringBuilder();
        for (Function function: functionList){
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

    public static ArgsParser getParsed() {
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
