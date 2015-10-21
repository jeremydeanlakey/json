package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jstring extends Json {
    private static final String QUOTE = "\"";
    private String value;

    protected Jstring(String value){ this.value = value; }

    @Override public boolean isString() { return true; }

    @Override public String getString(){ return value; }
    public boolean getBoolean(){
        if (value.equalsIgnoreCase("true")) return true;
        else if (value.equalsIgnoreCase("false")) return false;
        else throw new RuntimeException("Not a boolean value");
    }
    @Override public double getDouble() { try { return Double.valueOf(value); } catch (Exception e) { throw new RuntimeException("Not a number");} }
    @Override public long getLong() { try { return Long.valueOf(value); } catch (Exception e) { throw new RuntimeException("Not a number");} }

    @Override public String toString() { return "\"" + value + "\""; }
    @Override protected void writeTo(StringBuilder builder) { builder.append(QUOTE).append(value).append(QUOTE); }

    @Override public boolean equals(Object o) { return (o instanceof Jstring) && ((Jstring)o).getString().equals(value); }
}
