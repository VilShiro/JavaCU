package org.fbs.jcu.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.fbs.jcu.data.*;
import org.fbs.jcu.exception.AppException;
import org.fbs.jcu.exception.ArgsException;
import org.fbs.jcu.util.Saver;
import org.fbs.sava.data.SaveData;
import org.fbs.sava.data.SaveFile;
import org.fbs.sava.data.SaveValue;

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
            new Option("-artifactsFolder", "-af", "out/artifacts/", "Set folder name for artifacts - jars(divide folders using '/')"),
            new Option("-resourcesFolder","-rf","resources/","Set name of resources directory(divide folders using '/')(contain directory for external libraries)"),
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
    public void run() throws IOException, AppException {
        String standardPath = "/src/main/java";
        if (options[4].getValue().toString().toCharArray()[0] != '/'){
            standardPath += "/";
        }

        String srcPath = options[5].getValue() + standardPath + options[4].getValue() + (options[4].getValue().toString().toCharArray()[options[4].getValue().toString().toCharArray().length-1] == '/'? "" : "/");
        File srcFolders = new File(srcPath);
        if (!srcFolders.mkdirs()){
          throw new AppException("It wasn't possible to create a src/main/java/ directory and a standard package");
        }
        
        String classesPath = options[5].getValue() + "/" + options[1].getValue() + "/";
        File classesFolders = new File(classesPath);
        if (!classesFolders.mkdirs()){
            throw new AppException("Couldn't create an output directory for Java classes");
        }

        String artifactsPath = options[5].getValue() + "/" + options[2].getValue() + "/";
        File artifactsFolders = new File(artifactsPath);
        if (!artifactsFolders.mkdirs()){
            throw new AppException("Couldn't create an artifacts directories");
        }

        String resourcesPath = options[5].getValue() + "/" + options[3].getValue() + "/lib/";
        File resourcesFolders = new File(resourcesPath);
        if (!resourcesFolders.mkdirs()){
            throw new AppException("Couldn't create a resources and a lib directories");
        }

        if (keys[0].isValue()){
            String packageMain;
          
            if (options[4].getValue() == ""){
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
            String mainClass = options[5].getValue() + "/src/java/main/" + options[4].getValue() + "/" + options[0].getValue() + ".java";

            File mainFile = new File(mainClass);
            FileWriter mainWriter = new FileWriter(mainClass);

            if (!mainFile.createNewFile()){
                throw new AppException("Couldn't create " + options[0].getValue() + ".java");
            }

            mainWriter.write(mainText);
            mainWriter.close();
        }

        File configFile0 = new File(".cu/meta-config.sava");
        ArrayList<SaveData> saveData = new ArrayList<>();
        saveData.add(new SaveValue<>(options[4].getValue().toString(), 0, "packageDir", String.class));
        saveData.add(new SaveValue<>(options[3].getValue().toString(), 1, "resourcesDir", String.class));
        saveData.add(new SaveValue<>(options[2].getValue().toString(), 2, "artifactsDir", String.class));
        saveData.add(new SaveValue<>(options[1].getValue().toString(), 3, "classesDir", String.class));
        saveData.add(new SaveValue<>(options[0].getValue().toString(), 4, "projectName", String.class));
        Saver.save(new SaveFile(saveData), configFile0);

        // CREATE: 19.12.2023: create an app for creating java project in console
        
    }
}
