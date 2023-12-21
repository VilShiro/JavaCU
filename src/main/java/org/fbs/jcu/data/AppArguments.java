package org.fbs.jcu.data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppArguments {

    public AppArguments(Option @NotNull [] options, Key[] keys, Function @NotNull [] functions){
        this.functions = new ArrayList<>();
        this.functions.addAll(Arrays.asList(functions));
        this.keys = new ArrayList<>();
        this.keys.addAll(Arrays.asList(keys));
        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
    }

    private boolean requiredFunction = false;
    private boolean canContainOptions = true;
    private boolean canContainKeys = true;
    private boolean canContainFunctions = true;

    private final List<Option> options;
    private final List<Key> keys;
    private final List<Function> functions;

    public void addToOptions(Option option){
        options.add(option);
    }

    public void addToKeys(Key key){
        keys.add(key);
    }

    public void addToFunctions(Function function){
        functions.add(function);
    }

    public boolean isRequiredFunction() {
        return requiredFunction;
    }

    public void setRequiredFunction(boolean requiredFunction) {
        this.requiredFunction = requiredFunction;
    }

    public boolean isCanContainOptions() {
        return canContainOptions;
    }

    public void setCanContainOptions(boolean canContainOptions) {
        this.canContainOptions = canContainOptions;
        if (!canContainFunctions){
            requiredFunction = false;
        }
    }

    public boolean isCanContainKeys() {
        return canContainKeys;
    }

    public void setCanContainKeys(boolean canContainKeys) {
        this.canContainKeys = canContainKeys;
    }

    public boolean isCanContainFunctions() {
        return canContainFunctions;
    }

    public void setCanContainFunctions(boolean canContainFunctions) {
        this.canContainFunctions = canContainFunctions;
    }

    public List<Option> getOptions() {
        return options;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public List<Function> getFunctions() {
        return functions;
    }
}
