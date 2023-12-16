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

    private boolean standardValue = false;
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

    public boolean isStandardValue() {
        return standardValue;
    }

    public void call(){
        standardValue = true;
        onSetting();
    }

    private void onSetting() {
    }

}
