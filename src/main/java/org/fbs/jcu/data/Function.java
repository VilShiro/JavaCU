package org.fbs.jcu.data;

public abstract class Function {

    public Function(String typing){
        this.typing = typing;
        this.options = new Option[]{};
        this.keys = new Key[]{};
    }
    public Function(String typing, String info){
        this.info = info;
        this.typing = typing;
        this.options = new Option[]{};
        this.keys = new Key[]{};
    }
    public Function(String typing, Option[] options, Key[] keys){
        this.typing = typing;
        this.options = options;
        this.keys = keys;
    }
    public Function(String typing, Option[] options, Key[] keys, String info){
        this.typing = typing;
        this.info = info;
        this.options = options;
        this.keys = keys;
    }

    private final String typing;
    private String info;
    private Option[] options;
    private Key[] keys;

    public void setOptions(Option[] options) {
        this.options = options;
    }

    public void setKeys(Key[] keys) {
        this.keys = keys;
    }

    public Key[] getKeys() {
        return keys;
    }

    public Option[] getOptions() {
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

    @Override
    public String toString() {
        return typing + " ".repeat(Math.max(0, 35 - (typing.toCharArray().length))) + info;
    }
}
