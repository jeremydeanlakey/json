package com.jeremydeanlakey.json;

import android.util.Pair;

import java.io.EOFException;

/**
 * Created by jeremydeanlakey on 8/15/15.
 */
public class Jparser {
    String src;
    int loc = 0;

    public static Json stringToJson(String src) { return (new Jparser(src)).getJson(); }

    public Jparser(String src) { this.src = src; }

    private char peek() { return src.charAt(loc); }
    private boolean peek(char c) { return !done() && (peek() == c); }
    private char next() { return src.charAt(++loc); }
    private boolean done() { return loc >= src.length(); }
    private static boolean isWhiteSpaceChar(char c) { return c == ' '; } // TODO add other whitespace chars
    private boolean white() { return isWhiteSpaceChar(peek()); }
    private boolean sQuote() { return peek() == '\''; }
    private boolean dQuote() { return peek() == '\"'; }
    private boolean quote() { return dQuote() || sQuote(); }
    private boolean comma() { return peek() == ','; }
    private boolean colon() { return peek() == ':'; }
    private boolean arrayOpen() { return peek() == '['; }
    private boolean arrayClose() { return peek() == ']'; }
    private boolean objectOpen() { return peek() == '{'; }
    private boolean objectClose() { return peek() == '}'; }

    private void requireDone() { if (!done()) throw new RuntimeException("Expected end of string, but got " + peek() + " at " + loc); }
    private void requireNotDone(char c) { if (done()) throw new RuntimeException("End of string but xpected " + c); }
    private void requireNext(char c) { char n = next(); if (n != c) throw new RuntimeException(n + " but expected " + c); }
    private void skipWhite() { while (!done() && white()) loc++; }
    private void skipColon() { skipWhite(); requireNotDone(':'); requireNext(':'); }
    private void skipComma() { skipWhite(); requireNotDone(','); requireNext(','); }

    private double getNumber() { return 0; } // TODO
    private String getString() { skipWhite(); return null; } // TODO

    private Pair<String, Json> getKeyValue() { return null; }
    private Json getJobject() {
        Jobject object = new Jobject();
        skipWhite();
        requireNext('{');
        Pair<String, Json> keyValue = getKeyValue();
        while (keyValue != null) {
            object.put(keyValue.first, keyValue.second);
            keyValue = getKeyValue();
            skipWhite();
            if (peek('}')) break;
            requireNext(',');
        }
        skipWhite();
        requireNext('}');
        return object;
    }

    private Json getItem() {
        /*
        skipWhite();
        if (peek('{')) return getJobject();
        if (peekArray()) return getJarray();
        if (peekString()) return getJstring();
        if (peekNumber()) return getJnumber();
        if (peekBoolean()) return getJbool();
        if (peekNull()) return getJnull();
        */
        return null;
    }

    private Json getJson() {
        if (src == null)
            return null;
        Json item = getItem();
        skipWhite();
        requireDone();
        return item;
    }
}
