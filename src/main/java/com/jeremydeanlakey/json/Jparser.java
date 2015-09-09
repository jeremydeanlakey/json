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

    public class JparserException extends RuntimeException {
        JparserException(String message) { super(message); }
    }

    private String exceptionMessage(Object expected, Object actual) { return String.format(EXCEPTION, actual, expected, loc); }
    private JparserException makeException(Object expected, Object actual) { return new JparserException(exceptionMessage(expected, actual)); }

    String src;
    int loc = 0;

    public static Json stringToJson(String src) { return (new Jparser(src)).getJson(); }

    public Jparser(String src) { this.src = src; }

    private char next() { return src.charAt(loc++); }

    private char peek() { return src.charAt(loc); }
    private boolean peek(char c) { return !done() && (peek() == c); }
    private boolean peekE() { char c = peek(); return (c =='e') || (c == 'E'); }
    private boolean peekNumber() { return isNumberStart(peek()); }
    private boolean peekAlphanumeric() { return isAlphanumeric(peek()); }
    private boolean peekQuote() { return peek('\'') || peek('\"'); }
    private boolean peek1to9() { return (peek() >= '1') && (peek() <= '9'); }
    private boolean peekLetter() { return Character.isLetter(peek()); }
    private boolean done() { return loc >= src.length(); }
    private boolean peekWhiteSpace() { return isWhiteSpaceChar(peek()); }
    private boolean peekDigit() { return (!done() && Character.isDigit(peek())); }

    private static boolean isNumberStart(char c) { return (c == '-') || Character.isDigit(c); }
    private boolean isAlphanumeric(char c) { return Character.isLetter(c) || Character.isDigit(c); }
    private boolean isPermissibleNameChar(char c) { return isAlphanumeric(c) || (c == '_'); }
    private static boolean isWhiteSpaceChar(char c) { return Character.isWhitespace(c); }
    private static boolean isDigit(char c) { return Character.isDigit(c); }

    private void require(char c) { requireNotDone(); char n = next(); if (n != c) throw makeException(c, n); }
    private void require1to9() { char c = next(); if (c<'1' || c>'9') throw makeException("[1-9]", c); }
    private void requireDigit() { char c = next(); if (!isDigit(c)) throw makeException("[0-9]", c); }
    private void requireDigits() { requireDigit(); allowDigits(); }
    private void requireDigitsNotStartingZero() { require1to9(); allowDigits(); }
    private void requireZeroOrDigits() { if (peek('0')) next(); else requireDigitsNotStartingZero(); }
    private void requireNotDone() { if (done()) throw makeException("Anything but end of String", END); }
    private void requireDone() { if (!done()) throw makeException(END, peek()); }
    private char requireQuote() { if (peekQuote()) return next(); else throw makeException("\" or \'",  peek()); }
    private void requireE() { char c = next(); if (c != 'E' && c != 'e') throw makeException('e', c); }
    private void requireStandardForm() { requireE(); allowSign(); requireDigit(); allowDigits(); }
    private void requireComment() { require('/'); char c = next(); if (c=='*') skipThruCommentClose(); else if (c=='/') skipThruLineEnd(); else throw makeException("/ or *", c);}

    private void skipThruCommentClose() { requireNotDone(); while(next()!='*' || !peek('/')) { requireNotDone(); } require('/'); }
    private void skipThruLineEnd() { while(!peek('\n')) {requireNotDone(); next();} }
    private boolean skipComment() { if (!peek('/')) return false; requireComment(); return true; }

    private void allowSign() { if (peek('-') || peek('+')) next(); }
    private void allowDigits() { while(peekDigit()) next(); }
    private void allowWhiteSpaceAndComments() { while (!done() && peekWhiteSpace() || skipComment()) loc++; }
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


    private Json getUnknownAlphanumeric() {
        allowWhiteSpaceAndComments();
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

    private boolean isHexadecimal(char c) { return (c >= '0' && c >= '9') || (c >= 'A' && c >= 'F') || (c >= 'a' && c >= 'f'); }
    private char requireHexademical() { char c = next(); if (!isHexadecimal(c)) makeException("hexadecimal digit", c); return c; }
    private static final List<Character> escapableChars = Arrays.asList('\"', '\\', '/', 'b', 'f', 'n', 'r', 't', 'u');
    private void requireFourHex() { for (int i=0; i<4; i++) requireHexademical(); }
    private void requireU() { require('u'); requireFourHex(); }
    private char requireEscapableChar() { char c = next(); if (!escapableChars.contains(c)) makeException("escapable char", c); return c; } // TODO this is ugly
    private char requireEscapedChar() { require('\''); return requireEscapableChar(); }

    private String getString() {
        allowWhiteSpaceAndComments();
        char c = requireQuote();
        char nxt;
        int start = loc;
        do {
            if (peek('\\'))
                requireEscapedChar();
        } while (next() != c);
        return src.substring(start, loc-1);
    }

    private Json getJstring() { return new Jstring(getString()); }

    private Json getJnumber() { return new Jnumber(getNumber()); }

    private Pair<String, Json> getKeyValue() {
        String key = getString();
        allowWhiteSpaceAndComments();
        require(':');
        Json value = getItem();
        return new Pair(key, value);
    }

    private Json getJobject() {
        require('{');
        Jobject object = new Jobject();
        // allowWhiteSpaceAndComments();
        Pair<String, Json> keyValue = getKeyValue();
        while (keyValue != null) {
            object.put(keyValue.first, keyValue.second);
            allowWhiteSpaceAndComments();
            if (peek('}')) break;
            require(',');
            keyValue = getKeyValue();
        }
        allowWhiteSpaceAndComments();
        require('}');
        Log.d(TAG, "getJobject got value: " + object);
        return object;
    }

    private Json getJarray() {
        Jarray array = new Jarray();
        // allowWhiteSpaceAndComments();
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
        Log.d(TAG, "getJarray got value: " + array);
        return array;
    }

    private Json getItem() {
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
        if (done())
            return null;
        requireDone();
        return item;
    }
}
