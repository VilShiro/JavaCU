package org.fbs.jcu.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.fbs.jcu.data.*;
import org.fbs.jcu.exception.ArgsException;

public class CJP extends App {

    private static AppArguments appArguments;

    public CJP(String[] args, AppArguments appArguments) throws Exception {
        super(args, appArguments, false, false, "createjp", "org.fbs.jcu.app.CJP");
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
        new Option("-packageName", "-pkn", "/", "Set your package name(divide folders using '/')"),
        new Option("-projectName", "-pjn", true, "Set your project name"),
    };
    private static final Key[] keys = new Key[]{
        new Key("--createMain", "--cm"),
    };
    private static final Function[] functions = new Function[]{};

    public static void main(String[] args) throws Exception {
        appArguments = new AppArguments(options, keys, functions);
        new CJP(args, appArguments);
    }

    @Override
    public void run() throws IOException{
        String srcPath = options[3].getValue() + "/src/" + options[2].getValue() + "/";
        File srcFolders = new File(srcPath);
        if (!srcFolders.mkdirs()){
          
        }
        
        String classesPath = options[3].getValue() + "/" + options[1].getValue() + "/";
        File classesFolders = new File(classesPath);
        classesFolders.mkdirs();
        
        if (keys[0].isValue()){
          String packageMain;
          
          if (options[2].getValue() == "/"){
            packageMain = "";
          }
          else {
            packageMain = "package " + options[2].getValue().toString().replaceAll("/", ".") + ";\n";
          }
          
          String mainText = String.format(
          """
          %s
          public class %s{
            public static void main(String[] args) {
              /* code */
            }
          }
          
          """, packageMain, options[0].getValue());
          String mainClass = options[3].getValue() + "/src/" + options[2].getValue() + "/" + options[0].getValue() + ".java";
          
          File mainFile = new File(mainClass);
          FileWriter fileWriter = new FileWriter(mainClass);
          
          mainFile.createNewFile();
          fileWriter.write(mainText);
          fileWriter.close();
          
        }
        
        // CREATE: 19.12.2023: create an app for creating java project in console
        
    }
}
