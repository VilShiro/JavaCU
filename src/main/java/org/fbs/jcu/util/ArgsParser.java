package org.fbs.jcu.util;

import org.fbs.jcu.data.Key;
import org.fbs.jcu.data.Option;
import org.fbs.jcu.exception.ArgsException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("all")
public class ArgsParser {

    // Parse argument can be:
    //
    // option - word, before it must be "-"
    // value - word who been after option
    // key - word, before it must be "--", and it can't contain value itself

    public ArgsParser(String @NotNull [] args, Option[] options, Key[] keys, boolean mustContainsArgs) throws ArgsException {
        if (args.length == 0 && mustContainsArgs){
            throw new ArgsException("Does not have an args.");
        }
        parse(args, options, keys);
    }

    private final List<Option> options = new ArrayList<>();
    private final List<Key> keys = new ArrayList<>();

    @Contract(pure = true)
    private void parse(String @NotNull [] args, Option[] options, Key[] keys) throws ArgsException {
        boolean parseOption = false;
        Option option = null;

        for (String arg: args){
            if (parseOption){
                if ((arg.toCharArray()[0] == '-' && arg.toCharArray()[1] != '-') && (arg.toCharArray()[0] == '-' && arg.toCharArray()[1] == '-')) {
                    option.setValue(arg);
                    this.options.add(option);
                    parseOption = false;
                    continue;
                }
            }
            if (arg.toCharArray()[0] == '-' && arg.toCharArray()[1] != '-') {
                List<String> optionTyping = optionsTypingToArr(options);
                List<String> optionAlias = optionsAliasToArr(options);
                if (arrayHas(optionTyping.toArray(), arg) || arrayHas(optionAlias.toArray(), arg)) {
                    parseOption = true;
                    option = searchInOptionsByArg(options, arg);
                    continue;
                }
                else{
                    throw new ArgsException("Unknown option: " + arg);
                }
            }
            else if (arg.toCharArray()[0] == '-' && arg.toCharArray()[1] == '-') {
                List<String> keyTyping = keysTypingToArr(keys);
                List<String> keyAlias = keysAliasToArr(keys);
                if (arrayHas(keyTyping.toArray(), arg) || arrayHas(keyAlias.toArray(), arg)) {
                    this.keys.add(searchInKeysByArg(keys, arg));
                    continue;
                }
                else{
                    throw new ArgsException("Unknown key: " + arg);
                }
            }
            else {
                throw new ArgsException("Unknown argument: " + arg);
            }
        }
    }

    public List<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public List<Key> getKeys() {
        return Collections.unmodifiableList(keys);
    }

    private @Nullable Option searchInOptionsByArg(Option @NotNull [] options, String arg){
        for (Option option: options){
            if (Objects.equals(option.getAlias(), arg) || Objects.equals(option.getTyping(), arg)){
                return option;
            }
        }
        return null;
    }

    private @Nullable Key searchInKeysByArg(Key @NotNull [] keys, String arg){
        for (Key key: keys){
            if (Objects.equals(key.getAlias(), arg) || Objects.equals(key.getTyping(), arg)){
                return key;
            }
        }
        return null;
    }

    private @NotNull List<String> keysAliasToArr(Key @NotNull [] keys) {
        List<String> typing = new ArrayList<>();
        for (Key key: keys){
            typing.add(key.getTyping());
        }
        return typing;
    }

    private @NotNull List<String> keysTypingToArr(Key @NotNull [] keys) {
        List<String> typing = new ArrayList<>();
        for (Key key: keys){
            typing.add(key.getTyping());
        }
        return typing;
    }

    @Contract(pure = true)
    private @NotNull List<String> optionsTypingToArr(Option @NotNull [] options){
        List<String> typing = new ArrayList<>();
        for (Option option: options){
            typing.add(option.getTyping());
        }
        return typing;
    }
    @Contract(pure = true)
    private @NotNull List<String> optionsAliasToArr(Option @NotNull [] options){
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
