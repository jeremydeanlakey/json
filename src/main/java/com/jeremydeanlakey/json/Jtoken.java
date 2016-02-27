package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 2/25/16.
 */
class Jtoken {
    private char c;
    private Json json;

    protected Jtoken(char c) { this.c = c; }
    protected Jtoken(Json json) { this.json = json; }

    protected boolean isJsonValue() { return json != null; }
    protected Json getJsonValue() { return json; }
    protected boolean isObjectStart() { return (c == '{'); }
    protected boolean isObjectEnd() { return (c == '}'); }
    protected boolean isColon() { return (c == ':'); }
    protected boolean isArrayStart() { return (c == '['); }
    protected boolean isArrayEnd() { return (c == ']'); }
    protected boolean isComma() { return (c == ','); }
}


class Jtokenizer {
    String source;
    int position = 0;
    protected Jtokenizer(String source) { this.source = source; }
    protected Jtoken nextToken() { return null; } // TODO
}