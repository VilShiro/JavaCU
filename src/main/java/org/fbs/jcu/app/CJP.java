package org.fbs.jcu.app;

import org.fbs.jcu.data.*;
import org.fbs.jcu.exception.ArgsException;

public class CJP extends App {

    private static AppArguments appArguments;

    public CJP(String[] args, AppArguments appArguments) throws ArgsException {
        super(args, appArguments, false, "createjp", "org.fbs.jcu.app.CJP");
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
            new Option("-projectName", "-pj", true, "Set name for your project"),
            new Option("-packageName", "-pn", true, "Set your package name(divide folders using '/')"){
                @Override
                public void onSetting() {
                    projectPackage = getValue().toString();
                }
            },
            //new Option("-appName", "-an", true, ""){
                //@Override
                //public void onSetting() {
                    //funcName = getValue().toString();
                //}
            //}

    };
    private static final Key[] keys = new Key[]{
            new Key("--createMain", "--cm"),
    };
    private static final Function[] functions = new Function[]{
            new Function("help") {
                @Override
                public void call() {
                    System.out.printf(
                            """
                            There is a options, keys and functions for "%s
                            """, appName + "\""+
                                    "\n\nOptions(required has \"*\", else standard value in \"()\"):\n" + getOptionsAsString(appArguments) +
                                    "\nKeys:\n" + getKeysAsString(appArguments) +
                                    "\nFunctions:\n" + getFunctionsAsString(appArguments)
                    );
                }
            },
            new Function("huy") {
                @Override
                public void call() {
                    System.out.println(0);
                }
            }
    };

    public static void main(String[] args) throws ArgsException {
        functions[0].setOptions(new Option[]{
                new Option("-option", "-op", "opt"),
                new Option("-argopt", "-ao", "argo")
        });
        functions[0].setKeys(new Key[]{
                new Key("--key0", "-k")
        });
         appArguments = new AppArguments(options, keys, functions);

        new CJP(args, appArguments);
    }

    @Override
    public void run(){
        if (!getParsed().getFunctions().isEmpty()){
            getParsed().getFunctions().get(0).call();
        }
        else{
            // CREATE: 19.12.2023: create an app for creating java project in console
        }
        System.out.println(getBashApp());
        System.out.println(getBashAppComplete());
    }
}
