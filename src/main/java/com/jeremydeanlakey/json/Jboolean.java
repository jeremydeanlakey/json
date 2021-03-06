package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jboolean extends Json {
    private boolean value;

    protected Jboolean(boolean value){this.value = value;}

    @Override public boolean getBoolean(){return value;}

    @Override public boolean isBoolean() {return true;}

    @Override public String toString() { return String.valueOf(value); }
    @Override protected void writeTo(StringBuilder builder) { builder.append(String.valueOf(value)); }

    @Override public boolean equals(Object o) { return (o instanceof Jboolean) && ((Jboolean)o).getBoolean() ==     value; }
}
