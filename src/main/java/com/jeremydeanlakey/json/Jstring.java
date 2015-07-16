package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jstring extends Json {
    private String value;

    public Jstring(String value){
        this.value = value;
    }

    @Override
    public boolean isString() { return true; }

    @Override
    public String getString(){
        return value;
    }

    @Override
    public String toString() { return "\"" + value + "\""; }
}
