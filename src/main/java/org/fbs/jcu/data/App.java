package org.fbs.jcu.data;

import org.fbs.jcu.exception.ArgsException;
import org.fbs.jcu.util.ArgsParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class App {

    public App(String[] args, AppArguments appArguments, boolean mustContainsArgs, String appName, String projectPackage) throws ArgsException {
        this.args = args;
        this.appArguments = appArguments;
        this.argsParser = new ArgsParser(args, appArguments, mustContainsArgs);
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
        for (Function function: appArguments.getFunctions()){
            functions.append(function.getTyping()).append(" ");
        }

        class FunctionsComplete{
            private final List<String> functionComplete = new ArrayList<>();
            private final List<String> functionCompleteName = new ArrayList<>();
            private final List<String> functionTyping = new ArrayList<>();

            public void add(String functionComplete, String functionCompleteName, String functionTyping){
                this.functionComplete.add(functionComplete);
                this.functionCompleteName.add(functionCompleteName);
                this.functionTyping.add(functionTyping);
            }

            public List<String> getFunctionComplete() {
                return functionComplete;
            }

            public List<String> getFunctionCompleteName() {
                return functionCompleteName;
            }

            public List<String> getFunctionTyping() {
                return functionTyping;
            }
        }

        FunctionsComplete functionsComplete = new FunctionsComplete();
        for (Function function: appArguments.getFunctions()){
            if (function.getOptions().length == 0 && function.getKeys().length == 0){
                continue;
            }
            String functionCompleteName;
            String functionComplete;
            String functionTyping;

            StringBuilder args = new StringBuilder();
            for (Option option: function.getOptions()){
                args.append(option.getTyping()).append(" ");
            }
            for (Key key: function.getKeys()){
                args.append(key.getTyping()).append(" ");
            }

            functionCompleteName = (String.format("_%s_arguments", function.getTyping()));
            functionComplete = (String.format(
                """
                _%s_arguments () {
                   local %s="%s"
                   COMPREPLY=($(compgen -W "%s" -- "$cur"))
                }
                """, function.getTyping(), function.getTyping() + "_comp", args.toString(), function.getTyping() + "_comp"));
            functionTyping = function.getTyping();
            functionsComplete.add(functionComplete, functionCompleteName, functionTyping);
        }

        StringBuilder completes = new StringBuilder();
        for (String string: functionsComplete.getFunctionComplete()){
            completes.append(string).append("\n");
        }

        StringBuilder ifsComplete = new StringBuilder();
        if (functionsComplete.getFunctionCompleteName().isEmpty()){
            ifsComplete.append(
                """
                    else
                      COMPREPLY=()
                """);
        }
        else {
            for (int i = 0; i < functionsComplete.getFunctionCompleteName().size(); i ++){
                ifsComplete.append(String.format(
                    """
                        elif [[ $prev == "%s" ]] && [[ $COMP_CWORD -eq 1 || -z $prev ]]; then
                          %s
                    """, functionsComplete.getFunctionTyping().get(i), functionsComplete.getFunctionCompleteName().get(i)));
            }
        }

        bashAppComplete = String.format(
            """
            %s
    
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
            %s
                fi
            }
              
            complete -F _%s_completions %s
                     
            ## %s
            """, completes, appName, options.toString(), keys.toString(), functions.toString(), ifsComplete.toString(), appName, appName, appName);
    }

    public static String getBashApp() {
        return bashApp;
    }

    public static String getBashAppComplete() {
        return bashAppComplete;
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
