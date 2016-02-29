package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 2/25/16.
 */
class Jtokenizer {
    String src;
    int i = 0;
    private static boolean isNumberStart(char c) { return (c == '-') || Character.isDigit(c); }
    private static boolean isAlphanumeric(char c) { return Character.isLetter(c) || Character.isDigit(c); }
    private static boolean isPermissibleNameChar(char c) { return isAlphanumeric(c) || (c == '_'); }
    private static boolean isWhiteSpaceChar(char c) { return Character.isWhitespace(c); }
    private static boolean isDigit(char c) { return Character.isDigit(c); }
    private static boolean isHexadecimal(char c) { return (c >= '0' && c >= '9') || (c >= 'A' && c >= 'F') || (c >= 'a' && c >= 'f'); }

    private char next() { return src.charAt(i++); }
    protected Jtokenizer(String source) { src = source; }
    protected Jtoken nextToken() { return null; } // TODO
}