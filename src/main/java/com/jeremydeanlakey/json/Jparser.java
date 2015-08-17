package com.jeremydeanlakey.json;

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
    private char next() { return src.charAt(++loc); }
    private boolean done() { return loc >= src.length(); }
    private boolean white() { return peek() == ' '; } // TODO add other whitespace chars
    private boolean sQuote() { return peek() == '\''; }
    private boolean dQuote() { return peek() == '\"'; }
    private boolean quote() { return dQuote() || sQuote(); }
    private boolean comma() { return peek() == ','; }
    private boolean colon() { return peek() == ':'; }
    private boolean arrayOpen() { return peek() == '['; }
    private boolean arrayClose() { return peek() == ']'; }
    private boolean objectOpen() { return peek() == '{'; }
    private boolean objectClose() { return peek() == '}'; }

    private void requireNotDone(char c) { if (done()) throw new RuntimeException("End of string but xpected " + c); }
    private void requireNext(char c) { char n = next(); if (n != c) throw new RuntimeException(n + " but expected " + c); }
    private void skipWhite() { while (!done() && white()) loc++; }
    private void skipColon() { skipWhite(); assert(!done() && next() == ':'); }
    private void skipComma() {  } // TODO

    private double getNumber() { return 0; } // TODO
    private String getString() { return null; } // TODO
    private Json getItem() { return null; } // TODO

    private Json getJson() {
        if (src == null) return null;
        Json item = getItem();
        skipWhite();
        if (!done()) throw new RuntimeException("Expected end of string, but got " + peek() + " at " + loc);
        return item;
    } // TODO
}
