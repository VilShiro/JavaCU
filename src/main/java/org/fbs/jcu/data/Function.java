package org.fbs.jcu.data;

public abstract class Function {

    public Function(String typing){
        this.typing = typing;
    }
    public Function(String typing, String info){
        this.typing = typing;
        this.info = info;
    }

    private final String typing;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTyping() {
        return typing;
    }

    public abstract void use();

    public void call(){
        use();
    }

    @Override
    public String toString() {
        return "Function: " + typing + "      " + info;
    }
}
