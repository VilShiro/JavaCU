package org.fbs.jcu.data;

public class Key {

    public Key(String typing, String alias){
        this.alias = alias;
        this.typing = typing;
    }

    public Key(String typing, String alias, String info){
        this.info = info;
        this.alias = alias;
        this.typing = typing;
    }

    private boolean value = false;
    private String info;
    private final String typing, alias;

    public String getInfo() {
        return info;
    }

    public String getTyping() {
        return typing;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isValue() {
        return value;
    }

    public void call(){
        value = true;
        onSetting();
    }

    private void onSetting() {
    }

    @Override
    public String toString() {
        return typing + " : " + alias + " | " + value + "      " + info;
    }
}
