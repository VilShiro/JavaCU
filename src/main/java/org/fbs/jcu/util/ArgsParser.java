package org.fbs.jcu.util;

import org.fbs.jcu.data.AppArguments;
import org.fbs.jcu.data.Function;
import org.fbs.jcu.data.Key;
import org.fbs.jcu.data.Option;
import org.fbs.jcu.exception.ArgsException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ArgsParser {

    // Parse argument can be:
    //
    // option - word, before it must be "-"
    // value - word who been after option
    // key - word, before it must be "--", and it can't contain value itself

    public ArgsParser(String @NotNull [] args, AppArguments appArguments, boolean mustContainsArgs) throws ArgsException {
        if (args.length == 0 && mustContainsArgs){
            throw new ArgsException("Does not have an arguments.");
        }
        parse(args, appArguments);
    }

    private final List<Option> options = new ArrayList<>();
    private final List<Key> keys = new ArrayList<>();
    private final List<Function> functions = new ArrayList<>();

    @Contract(pure = true)
    private void parse(String @NotNull [] args, @NotNull AppArguments appArguments) throws ArgsException {
        boolean parseOption = false;
        boolean functionOption = false;
        Option option = null;
        Function function = null;

        final List<Option> requiredOptions = new ArrayList<>();
        for (Option o: appArguments.getOptions()){
            if (o.isRequired()){
                requiredOptions.add(o);
            }
        }

        int i = -1;

        for (String arg: args){
            i ++;
            if (parseOption){
                if (!(arg.toCharArray()[0] == '-')) {
                    assert option != null;
                    option.setValue(arg);

                    if (functionOption){
                        this.functions.get(0).addOption(option);
                    }
                    else {
                        this.options.add(option);
                    }

                    parseOption = false;
                    continue;
                }
                else {
                    throw new ArgsException("\"" + arg + "\" is an invalid option value");
                }
            }
            if (arg.toCharArray()[0] == '-' && arg.toCharArray()[1] != '-') {
                if (!appArguments.isCanContainOptions()){
                    throw new ArgsException("App arguments can't contains options");
                }
                if (function != null){
                    List<String> optionTyping = optionsTypingToArr(appArguments.getOptions());
                    List<String> optionAlias = optionsAliasToArr(appArguments.getOptions());

                    List<String> functionOptionTyping = optionsTypingToArr(function.getOptions());
                    List<String> functionOptionAlias = optionsAliasToArr(function.getOptions());
                    if (arrayHas(functionOptionTyping.toArray(), arg) || arrayHas(functionOptionAlias.toArray(), arg)){
                        parseOption = true;
                        functionOption = true;
                        option = searchInOptionsByArg(function.getOptions(), arg);
                        continue;
                    }
                    else if (arrayHas(optionTyping.toArray(), arg) || arrayHas(optionAlias.toArray(), arg)) {
                        parseOption = true;
                        option = searchInOptionsByArg(appArguments.getOptions(), arg);
                        continue;
                    } else {
                        throw new ArgsException("Unknown option: " + arg);
                    }
                }
                else {
                    List<String> optionTyping = optionsTypingToArr(appArguments.getOptions());
                    List<String> optionAlias = optionsAliasToArr(appArguments.getOptions());
                    if (arrayHas(optionTyping.toArray(), arg) || arrayHas(optionAlias.toArray(), arg)) {
                        parseOption = true;
                        option = searchInOptionsByArg(appArguments.getOptions(), arg);
                        continue;
                    } else {
                        throw new ArgsException("Unknown option: " + arg);
                    }
                }
            }
            else if (arg.toCharArray()[0] == '-' && arg.toCharArray()[1] == '-') {
                if (!appArguments.isCanContainKeys()){
                    throw new ArgsException("App arguments can't contains keys");
                }
                if (function != null){
                    List<String> keyTyping = keysTypingToArr(appArguments.getKeys());
                    List<String> keyAlias = keysAliasToArr(appArguments.getKeys());

                    List<String> functionKeyTyping = keysTypingToArr(function.getKeys());
                    List<String> functionKeyAlias = keysAliasToArr(function.getKeys());
                    if (arrayHas(functionKeyTyping.toArray(), arg) || arrayHas(functionKeyAlias.toArray(), arg)) {
                        Objects.requireNonNull(searchInKeysByArg(function.getKeys(), arg)).call();
                        this.keys.add(searchInKeysByArg(function.getKeys(), arg));
                        continue;
                    }
                    else if (arrayHas(keyTyping.toArray(), arg) || arrayHas(keyAlias.toArray(), arg)) {
                        Objects.requireNonNull(searchInKeysByArg(appArguments.getKeys(), arg)).call();
                        this.keys.add(searchInKeysByArg(appArguments.getKeys(), arg));
                        continue;
                    } else {
                        throw new ArgsException("Unknown key: " + arg);
                    }
                }
                else{
                    List<String> keyTyping = keysTypingToArr(appArguments.getKeys());
                    List<String> keyAlias = keysAliasToArr(appArguments.getKeys());
                    if (arrayHas(keyTyping.toArray(), arg) || arrayHas(keyAlias.toArray(), arg)) {
                        Objects.requireNonNull(searchInKeysByArg(appArguments.getKeys(), arg)).call();
                        this.keys.add(searchInKeysByArg(appArguments.getKeys(), arg));
                        continue;
                    } else {
                        throw new ArgsException("Unknown key: " + arg);
                    }
                }
            }
            else if (searchInFunctionsByArg(appArguments.getFunctions(), arg) != null){
                if (!appArguments.isCanContainFunctions()){
                    throw new ArgsException("App arguments can't contains functions");
                }
                if (i != 0){
                    throw new ArgsException("Function must be first argument / Arguments can contains only one function");
                }
                this.functions.add(searchInFunctionsByArg(appArguments.getFunctions(), arg));
                function = searchInFunctionsByArg(appArguments.getFunctions(), arg);
            }
            else {
                throw new ArgsException("Unknown argument: " + arg);
            }
        }


        for (Option o: options){
            for (int j = 0; j < requiredOptions.size(); j ++){
                if (Objects.equals(requiredOptions.get(j), o)){
                    requiredOptions.remove(j);
                }
            }
        }
        if (!requiredOptions.isEmpty()){
            throw new ArgsException("App has required options, you didn't call all the options, not called options: " + Arrays.toString(requiredOptions.toArray()));
        }
    }

    public List<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public List<Key> getKeys() {
        return Collections.unmodifiableList(keys);
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public Function getFunction(){
        return functions.get(0);
    }

    @Contract(pure = true)
    private @Nullable Function searchInFunctionsByArg(@NotNull List<Function> functions, String arg){
        for (Function function: functions){
            if (Objects.equals(function.getTyping(), arg)){
                return function;
            }
        }
        return null;
    }

    private @Nullable Option searchInOptionsByArg(@NotNull List<Option> options, String arg){
        for (Option option: options){
            if (Objects.equals(option.getAlias(), arg) || Objects.equals(option.getTyping(), arg)){
                return option;
            }
        }
        return null;
    }

    private @Nullable Key searchInKeysByArg(@NotNull List<Key> keys, String arg){
        for (Key key: keys){
            if (Objects.equals(key.getAlias(), arg) || Objects.equals(key.getTyping(), arg)){
                return key;
            }
        }
        return null;
    }

    private @NotNull List<String> keysAliasToArr(@NotNull List<Key> keys) {
        List<String> typing = new ArrayList<>();
        for (Key key: keys){
            typing.add(key.getAlias());
        }
        return typing;
    }

    private @NotNull List<String> keysTypingToArr(@NotNull List<Key> keys) {
        List<String> typing = new ArrayList<>();
        for (Key key: keys){
            typing.add(key.getTyping());
        }
        return typing;
    }

    @Contract(pure = true)
    private @NotNull List<String> optionsTypingToArr(@NotNull List<Option> options){
        List<String> typing = new ArrayList<>();
        for (Option option: options){
            typing.add(option.getTyping());
        }
        return typing;
    }
    @Contract(pure = true)
    private @NotNull List<String> optionsAliasToArr(@NotNull List<Option> options){
        List<String> typing = new ArrayList<>();
        for (Option option: options){
            typing.add(option.getAlias());
        }
        return typing;
    }

    @Contract(pure = true)
    private boolean arrayHas(Object @NotNull [] objects, Object object){
        for (Object o: objects){
            if (o.equals(object)){
                return true;
            }
        }
        return false;
    }

}
