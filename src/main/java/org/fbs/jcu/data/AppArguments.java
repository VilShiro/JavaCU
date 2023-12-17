package org.fbs.jcu.data;

public class AppArguments {

    public AppArguments(Option[] options, Key[] keys, Function[] functions){
        this.functions = functions;
        this.keys = keys;
        this.options = options;
    }

    private boolean requiredFunction = false;
    private boolean canContainOptions = true;
    private boolean canContainKeys = true;
    private boolean canContainFunctions = true;

    private final Option[] options;
    private final Key[] keys;
    private final Function[] functions;

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

    public Option[] getOptions() {
        return options;
    }

    public Key[] getKeys() {
        return keys;
    }

    public Function[] getFunctions() {
        return functions;
    }
}
