package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 8/15/15.
 */
public class Jparser {
    String src;
    int location = 0;

    public static Json stringToJson(String src) { return (new Jparser(src)).getJson(); }

    public Jparser(String src) { this.src = src; }

    private boolean done() { return location >= src.length(); }

    private Json getJson() {
        return null;
    } // TODO
}
