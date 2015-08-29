package com.jeremydeanlakey.json;

import android.util.Pair;

/**
 * Created by jeremydeanlakey on 8/15/15.
 */
public class Jparser {
    private static final String END = "END";
    private static final String NOT_END = "NOT END";

    public class JsonException extends RuntimeException {
        JsonException(char expected, char actual) { super("Unexpected character: '" + actual + "'.  Expected: '" + expected + "' at " + loc + "."); }
        JsonException(String expected, String actual) { super("Unexpected character: '" + actual + "'.  Expected: '" + expected + "' at " + loc + "."); }
        JsonException(char expected, String actual) { super("Unexpected character: '" + actual + "'.  Expected: '" + expected + "' at " + loc + "."); }
        JsonException(String expected, char actual) { super("Unexpected character: '" + actual + "'.  Expected: '" + expected + "' at " + loc + "."); }
    }


    String src;
    int loc = 0;

    public static Json stringToJson(String src) { return (new Jparser(src)).getJson(); }

    public Jparser(String src) { this.src = src; }

    private char next() { return src.charAt(++loc); }

    private char peek() { return src.charAt(loc); }
    private boolean peek(char c) { return !done() && (peek() == c); }
    private boolean peekNumber() { return isNumberStart(peek()); }
    private boolean peekAlphanumeric() { return isAlphanumeric(peek()); }
    private boolean peekQuote() { return peek('\'') || peek('\"'); }
    private boolean peek1to9() { return (peek() >= '1') && (peek() <= '9'); }
    private boolean peekLetter() { return Character.isLetter(peek()); }
    private boolean done() { return loc >= src.length(); }
    private boolean white() { return isWhiteSpaceChar(peek()); }
    private boolean peekDigit() { return (!done() && Character.isDigit(peek())); }

    private static boolean isNumberStart(char c) { return (c == '-') || Character.isDigit(c); }
    private boolean isAlphanumeric(char c) { return Character.isLetter(c) || Character.isDigit(c); }
    private boolean isPermissibleNameChar(char c) { return isAlphanumeric(c) || (c == '_'); }
    private static boolean isWhiteSpaceChar(char c) { return c == ' '; } // TODO add other whitespace chars

    private boolean require1to9() { char c = next(); if (c<'1' || c>'9') throw new JsonException("[1-9]", c); }
    private void requireNotDone() { if (done()) throw new JsonException("Anything but end of String", END); }
    private void requireDone() { if (!done()) throw new JsonException(END, peek()); }
    private void requireQuote() { if (!peekQuote()) throw new JsonException("\" or \'",  peek()); }
    private void require(char c) { requireNotDone(); char n = next(); if (n != c) throw new JsonException(c, n); }
    private void requireNumberDone() {} // TODO
    private void requireDigitsNotStartingZero() { require1to9(); while(peekDigit()) next(); }
    private void requireZeroOrDigits() {
        if (peek('0')) {
            next(); return;
        }
        if (!peek1to9()) return; // TODO throw error
        while (peekDigit()) next();
    }

    private void allowWhiteSpace() { while (!done() && white()) loc++; }
    private void allowMinus() { if (!done() && peek('-')) next(); }
    private void allowStandardForm() {} // TODO
    private void allowDecimalAndDigits() {
        if (!peek('.'))
            return;
        next();
        while (peekDigit())
            next();
    }


    private double getNumber() {
        int start = loc;
        allowMinus();
        requireZeroOrDigits();
        allowDecimalAndDigits();
        allowStandardForm(); // requireE, allowSign, requireAtLeastOneDigit,
        requireNumberDone();
        String number = src.substring(start, loc);
        return Double.valueOf(number);
    }


    private Json getUnknownAlphanumeric() {
        allowWhiteSpace();
        int start = loc;
        while (!done() && !isPermissibleNameChar(peek()))
            next();
        String value = src.substring(start, loc);
        switch(value.toLowerCase()) {
            case "true":
                return new Jboolean(true);
            case "false":
                return new Jboolean(false);
            case "null":
                return new Jnull();
            default:
                return new Jstring(value);
        }
    }

    // TODO allow escape characters
    private String getString() {
        allowWhiteSpace();
        requireQuote();
        char c = next();
        int start = loc;
        while (next() != c) {}
        return src.substring(start, loc-1);
    }

    private Json getJstring() {
        return new Jstring(getString());
    }

    private Json getJnumber() {
        int start = loc;
        allowMinus();
        if (peek('0'))
            next();
        else
            requireDigitsNotStartingZero();

        return null; // TODO
    }

    private Pair<String, Json> getKeyValue() {
        String key = getString();
        allowWhiteSpace();
        require(':');
        Json value = getItem();
        return new Pair(key, value);
    }

    private Json getJobject() {
        require('{');
        Jobject object = new Jobject();
        // allowWhiteSpace();
        Pair<String, Json> keyValue = getKeyValue();
        while (keyValue != null) {
            object.put(keyValue.first, keyValue.second);
            keyValue = getKeyValue();
            allowWhiteSpace();
            if (peek('}')) break;
            require(',');
        }
        allowWhiteSpace();
        require('}');
        return object;
    }

    private Json getJarray() {
        Jarray array = new Jarray();
        // allowWhiteSpace();
        require('[');
        allowWhiteSpace();
        while (!peek(']')) {
            array.add(getItem());
            allowWhiteSpace();
            if (peek(']')) break;
            require(',');
        }
        allowWhiteSpace();
        require(']');
        return array;
    }

    private Json getItem() {
        allowWhiteSpace();
        if (done()) throw new JsonException("not-empty string", END);
        if (peek('{')) return getJobject();
        if (peek('[')) return getJarray();
        if (peekQuote()) return getJstring();
        if (peekNumber()) return getJnumber();
        if (peekAlphanumeric()) return getUnknownAlphanumeric();
        throw new JsonException("json value", peek());
    }

    private Json getJson() {
        if (src == null)
            return null;
        Json item = getItem();
        allowWhiteSpace();
        if (done())
            return null;
        requireDone();
        return item;
    }
}
