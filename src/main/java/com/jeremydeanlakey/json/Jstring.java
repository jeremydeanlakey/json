package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jstring extends Json {
    private String value;

    protected Jstring(String value){ this.value = value; }

    @Override public boolean isString() { return true; }

    @Override public String getString(){ return value; }

    @Override public String toString() { return "\"" + value + "\""; }

    @Override public boolean equals(Object o) { return (o instanceof Json) && ((Jstring)o).getString().equals(value); }
}
