package org.fbs.jcu.app;

import org.fbs.jcu.data.*;
import org.fbs.jcu.util.Saver;
import org.fbs.sava.data.SaveData;
import org.fbs.sava.data.SaveFile;
import org.fbs.sava.data.SaveValue;
import org.fbs.sava.data.SaveValueArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tester extends App {

    private static AppArguments appArguments;

    public Tester(String[] args, AppArguments appArguments) throws Exception {
        super(args, appArguments, false, true, "createjp", "org.fbs.jcu.app.Tester");
        run();
    }

    private static final Option[] options = new Option[]{
        new Option("-mainName", "-mn", "Main", "Set file name for file with main() method"){
            @Override
            public void onSetting(){
                callArg(keys[0]);
            }
        },
        new Option("-classesFolder", "-cf", "classes/", "Set folder name for compiled code(divide folders using '/')"),
        new Option("-packageName", "-pn", "true", "Set your package name(divide folders using '/')")
    };
    private static final Key[] keys = new Key[]{
        new Key("--createMain", "--cm"),
    };
    private static final Function[] functions = new Function[]{
            new Function("test-run", "Launching your project under controlled conditions") {
                @Override
                public void call() {
                    System.out.println(0);
                }
            },
            new Function("print-vars") {
                @Override
                public void call() {
                    StringBuilder optionsValues = new StringBuilder();
                    for (Option option: options){
                      optionsValues.append(option.getTyping()).append(" —————— ").append(option.getValue()).append("\n");
                    }
                    StringBuilder keysCalls = new StringBuilder();
                    for (Key key: keys){
                      keysCalls.append(key.getTyping()).append(" —————— ").append(key.isValue()).append("\n");
                    }
                    StringBuilder functionsCalls = new StringBuilder();
                    for (Function function: getParsed().getFunctions()){
                      functionsCalls.append(function.getTyping()).append(" —————— ").append("\n");
                    }
                    StringBuilder arguments = new StringBuilder();
                    for (String argument: getParsed().getArguments()){
                      arguments.append(argument).append(" ———————— ");
                    }

                    System.out.printf(
                        """
                        There is an options, keys and functions for "%s
                        """, appName + "\""+
                                "\n\nOptions:\n" + optionsValues +
                                "\nKeys:\n" + keysCalls +
                                "\nFunctions:\n" + functionsCalls +
                                "\nArguments:\n" + arguments
                );
                }
            }
    };

    public static void main(String[] args) throws Exception {
        functions[0].addOptions(new Option[]{
                new Option("-option", "-op", "opt"),
                new Option("-argopt", "-ao", "argo")
        });
        functions[0].addKeys(new Key[]{
                new Key("--key0", "-k"),
                new Key("--ff", "--f")
        });
        appArguments = new AppArguments(options, keys, functions);

        new Tester(args, appArguments);
    }

    @Override
    public void run() throws Exception {
        if (!getParsed().getFunctions().isEmpty()){
            getParsed().getFunction().call();
        }
        else{
            // CREATE: 19.12.2023: create an app for creating java project in console
        }
    }
}