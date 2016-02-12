package com.jeremydeanlakey.json;

import android.util.Log;
import android.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jeremydeanlakey on 8/15/15.
 */
public class Jparser {
    private static final String TAG = "JparserTest";
    private static final String END = "END";
    private static final String NOT_END = "NOT END";
    private static final String EXCEPTION = "Unexpected character: '%s'.  Expected: '%s' at %d.";
    private static final List<Character> escapableChars = Arrays.asList('\"', '\\', '/', 'b', 'f', 'n', 'r', 't', 'u');

    String src;
    int loc = 0;

    public class JparserException extends RuntimeException {
        JparserException(String message) { super(message); }
    }

    private String exceptionMessage(Object expected, Object actual) { return String.format(EXCEPTION, actual, expected, loc); }
    private JparserException makeException(Object expected, Object actual) { return new JparserException(exceptionMessage(expected, actual)); }

    public static Json stringToJson(String src) { return (new Jparser(src)).getJson(); }

    public Jparser(String src) { this.src = src; }

    private char next() { return src.charAt(loc++); }

    private char peek() { return src.charAt(loc); }
    private boolean peek(char c) { return !done() && (peek() == c); }
    private boolean peekE() { return peek('E') || peek('e'); }
    private boolean peekNumber() { return !done() && isNumberStart(peek()); }
    private boolean peekAlphanumeric() { return !done() && isAlphanumeric(peek()); }
    private boolean peekQuote() { return peek('\'') || peek('\"'); }
    private boolean done() { return loc >= src.length(); }
    private boolean peekWhiteSpace() { return !done() && isWhiteSpaceChar(peek()); }
    private boolean peekDigit() { return (!done() && Character.isDigit(peek())); }

    private static boolean isNumberStart(char c) { return (c == '-') || Character.isDigit(c); }
    private boolean isAlphanumeric(char c) { return Character.isLetter(c) || Character.isDigit(c); }
    private boolean isPermissibleNameChar(char c) { return isAlphanumeric(c) || (c == '_'); }
    private static boolean isWhiteSpaceChar(char c) { return Character.isWhitespace(c); }
    private static boolean isDigit(char c) { return Character.isDigit(c); }
    private boolean isHexadecimal(char c) { return (c >= '0' && c >= '9') || (c >= 'A' && c >= 'F') || (c >= 'a' && c >= 'f'); }

    private void require(char c) { requireNotDone(); char n = next(); if (n != c) throw makeException(c, n); }
    private void require1to9() { char c = next(); if (c<'1' || c>'9') throw makeException("[1-9]", c); }
    private void requireDigit() { char c = next(); if (!isDigit(c)) throw makeException("[0-9]", c); }
    private void requireDigitsNotStartingZero() { require1to9(); allowDigits(); }
    private void requireZeroOrDigits() { if (peek('0')) next(); else requireDigitsNotStartingZero(); }
    private void requireNotDone() { if (done()) throw makeException("Anything but end of String", END); }
    private void requireDone() { if (!done()) throw makeException(END, peek()); }
    private char requireQuote() { if (peekQuote()) return next(); else throw makeException("\" or \'",  peek()); }
    private void requireE() { char c = next(); if (c != 'E' && c != 'e') throw makeException('e', c); }
    private void requireStandardForm() { requireE(); allowSign(); requireDigit(); allowDigits(); }
    private void requireComment() { require('/'); char c = next(); if (c=='*') skipThruCommentClose(); else if (c=='/') skipThruLineEnd(); else throw makeException("/ or *", c);}
    private char requireHexademical() { char c = next(); if (!isHexadecimal(c)) throw makeException("hexadecimal digit", c); return c; }
    private void requireFourHex() { for (int i=0; i<4; i++) requireHexademical(); }
    private char requireEscapableChar() { if (!escapableChars.contains(peek())) throw makeException("escapable char", peek()); return next();}
    private char requireEscapedChar() { require('\''); return requireEscapableChar(); }

    private void skipThruCommentClose() { requireNotDone(); while(next()!='*' || !peek('/')) { requireNotDone();  } require('/'); }
    private void skipThruLineEnd() { while(!peek('\n') && !done()) {next();} }
    private boolean skipComment() { if (!peek('/')) return false; requireComment(); return true; }

    private void allowSign() { if (peek('-') || peek('+')) next(); }
    private void allowDigits() { while(peekDigit()) next(); }
    private void allowWhiteSpaceAndComments() { while (!done() && peekWhiteSpace() || peek('/')) {if (peek('/')) skipComment(); else loc++;} }
    private void allowMinus() { if (!done() && peek('-')) next(); }
    private void allowDecimalAndDigits() { if (peek('.')) { next(); allowDigits(); } }

    private double getNumber() {
        int start = loc;
        allowMinus();
        requireZeroOrDigits();
        allowDecimalAndDigits();
        if (peekE())
            requireStandardForm();
        String number = src.substring(start, loc);
        return Double.valueOf(number);
    }

    private String getUnquotedString() {
        allowWhiteSpaceAndComments();
        int start = loc;
        while (!done() && isPermissibleNameChar(peek()))
            next();
        return src.substring(start, loc);
    }

    protected Json getUnknownAlphanumeric() {
        String value = getUnquotedString();
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

    private String getQuotedString() {
        allowWhiteSpaceAndComments();
        char c = requireQuote();
        int start = loc;
        do {
            if (peek('\\')) {
                if (requireEscapedChar() == 'u')
                    requireFourHex();
            }
        } while (next() != c);
        return src.substring(start, loc-1);
    }

    private String getString() {
        allowWhiteSpaceAndComments();
        if (peekQuote())
            return getQuotedString();
        else
            return getUnquotedString();
    }

    protected Json getJstring() { Log.d(TAG, "calling getJstring at " + loc); return new Jstring(getString()); }

    protected Json getJnumber() { Log.d(TAG, "calling getJnumber at " + loc); return new Jnumber(getNumber()); }

    private Pair<String, Json> getKeyValue() {
        Log.d(TAG, "calling getKeyValue at " + loc);
        String key = getString();
        allowWhiteSpaceAndComments();
        require(':');
        Json value = getItem();
        return new Pair(key, value);
    }

    protected Json getJobject() {
        Log.d(TAG, "calling Jobject at " + loc);
        require('{');
        Jobject object = new Jobject();
        allowWhiteSpaceAndComments();
        if (peek('}')) {
            require('}'); return object;
        }
        Pair<String, Json> keyValue = getKeyValue();
        while (keyValue != null) {
            object.put(keyValue.first, keyValue.second);
            allowWhiteSpaceAndComments();
            if (peek('}')) break;
            require(',');
            allowWhiteSpaceAndComments();
            if (peek('}')) break;
            requireNotDone();
            keyValue = getKeyValue();
        }
        allowWhiteSpaceAndComments();
        require('}');
        Log.d(TAG, "getJobject got value: " + object);
        return object;
    }

    protected Json getJarray() {
        Jarray array = new Jarray();
        allowWhiteSpaceAndComments();
        require('[');
        allowWhiteSpaceAndComments();
        while (!peek(']')) {
            array.add(getItem());
            allowWhiteSpaceAndComments();
            if (peek(']')) break;
            require(',');
        }
        allowWhiteSpaceAndComments();
        require(']');
        return array;
    }

    protected Json getItem() {
        Log.d(TAG, "calling getItem at " + loc);
        allowWhiteSpaceAndComments();
        if (done()) throw makeException("not-empty string", END);
        if (peek('{')) return getJobject();
        if (peek('[')) return getJarray();
        if (peekQuote()) return getJstring();
        if (peekNumber()) return getJnumber();
        if (peekAlphanumeric()) return getUnknownAlphanumeric();
        throw makeException("json value", peek());
    }

    private Json getJson() {
        if (src == null)
            return null;
        Json item = getItem();
        allowWhiteSpaceAndComments();
        requireDone();
        return item;
    }
}
