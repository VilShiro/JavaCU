package org.fbs.jcu.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fbs.jcu.data.App.getKeysAsString;
import static org.fbs.jcu.data.App.getOptionsAsString;

public abstract class Function {

    public Function(String typing){
        this.typing = typing;
        this.options = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.keys.add(new Key("--help", "--help"));
    }
    public Function(String typing, String info){
        this.info = info;
        this.typing = typing;
        this.options = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.keys.add(new Key("--help", "--help"));
    }
    public Function(String typing, Option[] options, Key[] keys){
        this.typing = typing;
        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
        this.keys = new ArrayList<>();
        this.keys.addAll(Arrays.asList(keys));
        this.keys.add(new Key("--help", "--help"));
    }
    public Function(String typing, Option[] options, Key[] keys, String info){
        this.typing = typing;
        this.info = info;
        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(options));
        this.keys = new ArrayList<>();
        this.keys.addAll(Arrays.asList(keys));
        this.keys.add(new Key("--help", "--help"));
    }

    private final String typing;
    private String info;
    private final List<Option> options;
    private final List<Key> keys;

    public void addOptions(Option[] options){
        this.options.addAll(Arrays.asList(options));
    }

    public void addKeys(Key[] keys){
        this.keys.addAll(Arrays.asList(keys));
    }

    public void addOption(Option option){
        options.add(option);
    }

    public void addKey(Key key){
        keys.add(key);
    }

    public List<Key> getKeys() {
        return keys;
    }

    public List<Option> getOptions() {
        return options;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTyping() {
        return typing;
    }

    public abstract void call();

    public String toHelpString(){
        return typing + " ".repeat(Math.max(0, 35 - (typing.toCharArray().length))) + info + "\n    " + typing + "'s options and keys: " +
                "\nOptions(required has \"*\", else standard value in \"()\"):\n" + getOptionsAsString(options) +
                "\nKeys:\n" + getKeysAsString(keys);
    }

    @Override
    public String toString() {
        return typing + " ".repeat(Math.max(0, 35 - (typing.toCharArray().length))) + info;
    }
}
