package org.fbs.jcu.util;

import org.fbs.jcu.exception.SaverException;
import org.fbs.sava.data.SaveFile;
import org.fbs.sava.data.SaveData;
import org.fbs.sava.data.SaveType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Saver {

    private Saver(){}

    public static void save(@NotNull List<SaveData> saveDataList, String fileName) throws IOException{
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileName);

        SaveFile saveFile = new SaveFile();
        for (SaveData saveData: saveDataList){
            saveFile.addData(saveData);
        }

        StringBuilder text = new StringBuilder();
        String[] symbols = {"", ""};
        for (SaveData saveData: saveFile.getByStructType(SaveType.VALUE)){
            text.append("val ");
            text.append(saveData.getName()).append(" ");
            if (saveData.getValueClass().equals(Integer.class)) {
                text.append("int ");
            }
            else if (saveData.getValueClass().equals(Long.class)) {
                text.append("long ");
            }
            else if (saveData.getValueClass().equals(Double.class)) {
                text.append("double ");
            }
            else if (saveData.getValueClass().equals(String.class)) {
                text.append("str ");
                symbols[0] = "\"";
                symbols[1] = "\"";
            }
            else if (saveData.getValueClass().equals(Character.class)) {
                text.append("char ");
                symbols[0] = "'";
                symbols[1] = "'";
            }
            else if (saveData.getValueClass().equals(Boolean.class)) {
                text.append("bool ");
            }
            text.append("= ");
            text.append(symbols[0]).append(saveData.getValue()).append(symbols[1]).append(";");
            text.append("\n");
        }
        symbols[0] = "";
        symbols[1] = "";
        for (SaveData saveData: saveFile.getByStructType(SaveType.ARRAY)){
            text.append("array ");
            text.append(saveData.getName()).append(" ");
            if (saveData.getValueClass().equals(Integer.class)) {
                text.append("int ");
            }
            else if (saveData.getValueClass().equals(Long.class)) {
                text.append("long ");
            }
            else if (saveData.getValueClass().equals(Double.class)) {
                text.append("double ");
            }
            else if (saveData.getValueClass().equals(String.class)) {
                text.append("str ");
                symbols[0] = "\"";
                symbols[1] = "\"";
            }
            else if (saveData.getValueClass().equals(Character.class)) {
                text.append("char ");
                symbols[0] = "'";
                symbols[1] = "'";
            }
            else if (saveData.getValueClass().equals(Boolean.class)) {
                text.append("bool ");
            }
            text.append("{ ");
            if (saveData.getValues().size() > 1) {
                int valuesSize = saveData.getValues().size();
                int i = 0;
                for (Object object : saveData.getValues()) {
                    text.append(symbols[0]).append(object.toString()).append(symbols[1]).append((i == (valuesSize - 1))? " " : ", ");
                    i ++;
                }
                text.append(" };");
            }
            else if (saveData.getValues().size() == 1) {
                text.append(symbols[0]).append(saveData.getValues().get(0)).append(symbols[1]).append(" };");
            }
            text.append("\n");
        }
        boolean fileAlreadyCreated = false;
        for (File file1: Objects.requireNonNull(new File(System.getProperty("user.dir")).listFiles())){
            if (file1.getName().equals(fileName)) {
                fileAlreadyCreated = true;
                break;
            }
        }
        if (!fileAlreadyCreated) {
            if (!file.createNewFile()) {
                throw new SaverException("Failed to create config file");
            }
            else{
                fileWriter.write(text.toString());
                fileWriter.close();
            }
        }
        else {
            fileWriter.write(text.toString());
            fileWriter.close();

        }

    }
    public static void save(@NotNull SaveFile saves, @NotNull File configFile) throws IOException{
        String filePath = configFile.getPath();
        File file = new File(filePath);

        StringBuilder text = new StringBuilder();
        String[] symbols = {"", ""};
        for (SaveData saveData: saves.getByStructType(SaveType.VALUE)){
            text.append("val ");
            text.append(saveData.getName()).append(" ");
            if (saveData.getValueClass().equals(Integer.class)) {
                text.append("int ");
            }
            else if (saveData.getValueClass().equals(Long.class)) {
                text.append("long ");
            }
            else if (saveData.getValueClass().equals(Double.class)) {
                text.append("double ");
            }
            else if (saveData.getValueClass().equals(String.class)) {
                text.append("str ");
                symbols[0] = "\"";
                symbols[1] = "\"";
            }
            else if (saveData.getValueClass().equals(Character.class)) {
                text.append("char ");
                symbols[0] = "'";
                symbols[1] = "'";
            }
            else if (saveData.getValueClass().equals(Boolean.class)) {
                text.append("bool ");
            }
            text.append("= ");
            text.append(symbols[0]).append(saveData.getValue()).append(symbols[1]).append(";");
            text.append("\n");
        }
        symbols[0] = "";
        symbols[1] = "";
        for (SaveData saveData: saves.getByStructType(SaveType.ARRAY)){
            text.append("array ");
            text.append(saveData.getName()).append(" ");
            if (saveData.getValueClass().equals(Integer.class)) {
                text.append("int ");
            }
            else if (saveData.getValueClass().equals(Long.class)) {
                text.append("long ");
            }
            else if (saveData.getValueClass().equals(Double.class)) {
                text.append("double ");
            }
            else if (saveData.getValueClass().equals(String.class)) {
                text.append("str ");
                symbols[0] = "\"";
                symbols[1] = "\"";
            }
            else if (saveData.getValueClass().equals(Character.class)) {
                text.append("char ");
                symbols[0] = "'";
                symbols[1] = "'";
            }
            else if (saveData.getValueClass().equals(Boolean.class)) {
                text.append("bool ");
            }
            text.append("{ ");
            if (saveData.getValues().size() > 1) {
                int valuesSize = saveData.getValues().size();
                int i = 0;
                for (Object object : saveData.getValues()) {
                    text.append(symbols[0]).append(object.toString()).append(symbols[1]).append((i == (valuesSize - 1))? " " : ", ");
                    i ++;
                }
                text.append(" };");
            }
            else if (saveData.getValues().size() == 1) {
                text.append(symbols[0]).append(saveData.getValues().get(0)).append(symbols[1]).append(" };");
            }
            text.append("\n");
        }
        if (!new File(file.getParent() + "/").mkdirs()){
            throw new SaverException("Couldn't create a config directory(s)");
        }
        FileWriter fileWriter = new FileWriter(file);
        boolean fileAlreadyCreated = false;
        for (File file1: Objects.requireNonNull(new File(file.getParent()).listFiles())){
            if (file1.getName().equals(file.getName())) {
                fileAlreadyCreated = true;
                break;
            }
        }
        if (!fileAlreadyCreated) {
            if (!file.createNewFile()) {
                throw new SaverException("Failed to create config");
            }
            else{
                fileWriter.write(text.toString());
                fileWriter.close();
            }
        }
        else {
            fileWriter.write(text.toString());
            fileWriter.close();

        }

    }

}
