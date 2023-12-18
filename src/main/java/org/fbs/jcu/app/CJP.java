package org.fbs.jcu.app;

import org.fbs.jcu.data.*;
import org.fbs.jcu.exception.ArgsException;

import java.util.Arrays;

public class CJP extends App {

    public CJP(String[] args, AppArguments appArguments) throws ArgsException {
        super(args, appArguments, false, "createjp", "");
        System.out.println(Arrays.toString(getArgsParser().getKeys().toArray()));
    }

    private static final Option[] options = new Option[]{
            new Option("-mainName", "-mn", "Main", "Set file name for file with main() method"){
                @Override
                public void onSetting(){
                    callArg(keys[0]);
                }
            },
            new Option("-packageName", "-pn", true, "Set your package name(divide folders using '/')"),
            new Option("-classesFolder", "-cf", "classes/", "Set folder name for compiled code(divide folders using '/')"),
            new Option("-projectName", "-pj", true, "Set name for your project"),
            new Option("-projectPackage", "-pp", true, ""){
                @Override
                public void onSetting() {
                    projectPackage = getValue().toString();
                }
            },
            new Option("-appName", "-pp", true, ""){
                @Override
                public void onSetting() {
                    funcName = getValue().toString();
                }
            }
    };
    private static final Key[] keys = new Key[]{
            new Key("--createMain", "--cm")
    };
    private static final Function[] functions = new Function[]{};

    public static void main(String[] args) throws ArgsException {
        AppArguments appArguments = new AppArguments(options, keys, functions);
        appArguments.setCanContainFunctions(false);

        new CJP(args, appArguments);

        // CREATE: 16.12.2023: create an app for creating java project in console, has command in install.sh(preview)
    }

}
