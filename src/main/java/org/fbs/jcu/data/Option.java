package org.fbs.jcu.data;

public class Option {

    public Option(String typing, String alias, boolean required){
        this.typing = typing;
        this.alias = alias;
        this.required = required;
    }
    public Option(String typing, String alias, boolean required, String info){
        this.typing = typing;
        this.alias = alias;
        this.required = required;
        this.info = info;
    }
    public Option(String typing, String alias, String  standardValue){
        this.typing = typing;
        this.alias = alias;
        value = standardValue;
    }
    public Option(String typing, String alias, String  standardValue, String info){
        this.typing = typing;
        this.alias = alias;
        value = standardValue;
        this.info = info;
    }

    private String info;
    private boolean required = false;
    private final String typing, alias;
    private String value;

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void onSetting(){

    }

    public void setValue(String value) {
        this.value = value;
        onSetting();
    }

    public String getInfo() {
        return info;
    }

    public Object getValue() {
        return value;
    }

    public String getTyping() {
        return typing;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return typing + " : " + alias + " | " + value + "      " + info;
    }
}
