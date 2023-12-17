package org.fbs.jcu.util;

import org.fbs.sava.data.SaveFile;
import org.fbs.sava.data.SaveData;
import org.fbs.sava.data.SaveType;
import org.jetbrains.annotations.NotNull;

public class Saver {

    private Saver(){}

    public void save(@NotNull SaveFile saveFile){
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
                for (Object object : saveData.getValues()) {
                    text.append(symbols[0]).append(object).append(symbols[1]);
                }
            }
            else if (saveData.getValues().size() == 1) {
                text.append(symbols[0]).append(saveData.getValues().get(0)).append(symbols[1]).append(" };");
            }
            //text.append(symbols[0]).append(saveData.getValue()).append(symbols[1]).append(";");
        }
    }

}
