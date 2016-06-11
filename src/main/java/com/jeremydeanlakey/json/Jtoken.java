package com.jeremydeanlakey.json;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jeremydeanlakey on 2/25/16.
 */
class Jtoken {
    protected static Jtoken END = new Jtoken();
    private String END_STR = "END";
    private char c;
    private Json json;
    private String s;
    private Double d;
    private static List<Character> tokenChars = Arrays.asList('{', '}', ':', '[', ']', ',');

    private Jtoken() {}
    protected Jtoken(char c) { this.c = c; }
    protected Jtoken(String s) { this.s = s; }
    protected Jtoken(Double d) { this.d = d; }

    protected boolean isNumber() { return d != null; }
    protected Double getNumberValue() { return d; }
    protected boolean isStringValue() { return s != null; }
    protected String getStringValue() { return s; }
    protected boolean isJsonValue() { return json != null; }
    protected boolean isObjectStart() { return (c == '{'); }
    protected boolean isObjectEnd() { return (c == '}'); }
    protected boolean isColon() { return (c == ':'); }
    protected boolean isArrayStart() { return (c == '['); }
    protected boolean isArrayEnd() { return (c == ']'); }
    protected boolean isComma() { return (c == ','); }
    protected boolean isEnd() { return this == END; }

    public static Jtoken end() { return END; }
    public static boolean isValidToken(char c) { return tokenChars.contains(c); }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Jtoken)) return false;
        Jtoken otherToken = (Jtoken) other;
        return  (isEnd() && otherToken.isEnd())
                || (otherToken.isStringValue() && otherToken.getStringValue() == s)
                || (otherToken.isNumber() && otherToken.getNumberValue() == d)
                || (otherToken.c == c);
    }

    @Override
    public int hashCode() { return 0; } // TODO

    @Override
    public String toString() {
        if (isStringValue()) { return s; }
        else if (isEnd()) { return END_STR; }
        else if (isNumber()) { return d.toString(); }
        else if (isJsonValue()) { return json.toString(); }
        else { return String.valueOf(c); }
    }
}