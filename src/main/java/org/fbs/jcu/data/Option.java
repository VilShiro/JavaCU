package org.fbs.jcu.data;

public class Option {

    public Option(String typing, String alias, Class<?> valueClass, String  standardValue){
        this.typing = typing;
        this.alias = alias;
        this.valueClass = valueClass;
        value = standardValue;
    }

    public Option(String typing, String alias, Class<?> valueClass, String  standardValue, String info){
        this.typing = typing;
        this.alias = alias;
        this.valueClass = valueClass;
        value = standardValue;
        this.info = info;
    }

    private String info;
    private final String typing, alias;
    private final Class<?> valueClass;
    private String value;

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

    public Class<?> getValueClass() {
        return valueClass;
    }
}
