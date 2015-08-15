package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 8/15/15.
 */
public class Jparser {
    String src;
    int loc = 0;

    public static Json stringToJson(String src) { return (new Jparser(src)).getJson(); }

    public Jparser(String src) { this.src = src; }

    private boolean done() { return loc >= src.length(); }
    private boolean white() { return src.charAt(loc) == ' '; } // TODO add other whitespace chars
    private void skipWhite() { while (!done() && white()) loc++; }
    private Json getJson() {
        return null;
    } // TODO
}
