package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 2/25/16.
 */
class Jtoken {
    private char c;
    private Json json;
    private String s;

    protected Jtoken(char c) { this.c = c; }
    protected Jtoken(Json json) { this.json = json; }
    protected Jtoken(String s) { this.s = s; }

    protected boolean isStringValue() { return s != null; }
    protected String getStringValue() { return s; }
    protected boolean isJsonValue() { return json != null; }
    protected Json getJsonValue() { return json; }
    protected boolean isObjectStart() { return (c == '{'); }
    protected boolean isObjectEnd() { return (c == '}'); }
    protected boolean isColon() { return (c == ':'); }
    protected boolean isArrayStart() { return (c == '['); }
    protected boolean isArrayEnd() { return (c == ']'); }
    protected boolean isComma() { return (c == ','); }
}