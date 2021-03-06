package com.jeremydeanlakey.json;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jeremydeanlakey on 2/25/16.
 */
class Jtokenizer {
    private static final String END = "END";
    private static final String NOT_END = "NOT END";
    private static final String EXCEPTION = "Unexpected character: '%s'.  Expected: '%s' at %d.";
    private static final List<Character> escapableChars = Arrays.asList('\"', '\\', '/', 'b', 'f', 'n', 'r', 't', 'u');

    String src;
    int i = 0;
    int i_peek = 0;
    boolean peeking = false;
    Jtoken peekedToken;

    public class JparserException extends RuntimeException {
        JparserException(String message) { super(message); }
    }

    public int position() { if (peeking) return i_peek; else return i; }

    protected Jtoken peekToken() {
        if (!peeking) {
            i_peek = i;
            peekedToken = nextToken();
            peeking = true;
        }
        return peekedToken;
    }

    protected Jtoken nextToken() {
        if (peeking) { peeking = false; return peekedToken; }
        allowWhiteSpaceAndComments();
        if (done()) return Jtoken.end();
        if (peekQuote()) return new Jtoken(getQuotedString(), true);
        if (peek('-') || peekDigit()) return new Jtoken(getNumber());
        if (peekAlphanumeric()) return new Jtoken(getUnquotedString());
        if (Jtoken.isValidToken(peek())) return new Jtoken(next());
        throw new JparserException("Not a valid token: " + next());
    }


    private String exceptionMessage(Object expected, Object actual) { return String.format(EXCEPTION, actual, expected, i); }
    private JparserException makeException(Object expected, Object actual) { return new JparserException(exceptionMessage(expected, actual)); }


    private char next() { return src.charAt(i++); }

    private char peek() { return src.charAt(i); }
    private boolean peek(char c) { return !done() && (peek() == c); }
    private boolean peekE() { return peek('E') || peek('e'); }
    private boolean peekNumber() { return !done() && isNumberStart(peek()); }
    private boolean peekAlphanumeric() { return !done() && isAlphanumeric(peek()); }
    private boolean peekQuote() { return peek('\'') || peek('\"'); }
    private boolean done() { return i >= src.length(); }
    private boolean peekWhiteSpace() { return !done() && isWhiteSpaceChar(peek()); }
    private boolean peekDigit() { return (!done() && Character.isDigit(peek())); }

    private static boolean isNumberStart(char c) { return (c == '-') || Character.isDigit(c); }
    private static boolean isAlphanumeric(char c) { return Character.isLetter(c) || Character.isDigit(c); }
    private static boolean isPermissibleNameChar(char c) { return isAlphanumeric(c) || (c == '_'); }
    private static boolean isWhiteSpaceChar(char c) { return Character.isWhitespace(c); }
    private static boolean isDigit(char c) { return Character.isDigit(c); }
    public static boolean isHexadecimal(char c) { return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'); }

    private void require(char c) { requireNotDone(); char n = next(); if (n != c) throw makeException(c, n); }
    private void require1to9() { char c = next(); if (c<'1' || c>'9') throw makeException("[1-9]", c); }
    private void requireDigit() { char c = next(); if (!isDigit(c)) throw makeException("[0-9]", c); }
    private void requireDigitsNotStartingZero() { require1to9(); allowDigits(); }
    private void requireZeroOrDigits() { if (peek('0')) next(); else requireDigitsNotStartingZero(); }
    private void requireNotDone() { if (done()) throw makeException("Anything but end of String", END); }
    private char requireQuote() { if (peekQuote()) return next(); else throw makeException("\" or \'",  peek()); }
    private void requireE() { char c = next(); if (c != 'E' && c != 'e') throw makeException('e', c); }
    private void requireStandardForm() { requireE(); allowSign(); requireDigit(); allowDigits(); }
    private void requireComment() { require('/'); char c = next(); if (c=='*') skipThruCommentClose(); else if (c=='/') skipThruLineEnd(); else throw makeException("/ or *", c);}
    private char requireHexademical() { char c = next(); if (!isHexadecimal(c)) throw makeException("hexadecimal digit", c); return c; }
    private void requireFourHex() { for (int i=0; i<4; i++) requireHexademical(); }
    private char requireEscapableChar() { if (!escapableChars.contains(peek())) throw makeException("escapable char", peek()); return next();}
    private char requireEscapedChar() { require('\\'); return requireEscapableChar(); }

    private void skipThruCommentClose() { requireNotDone(); while(next()!='*' || !peek('/')) { requireNotDone();  } require('/'); }
    private void skipThruLineEnd() { while(!peek('\n') && !done()) {next();} }
    private boolean skipComment() { if (!peek('/')) return false; requireComment(); return true; }

    private void allowSign() { if (peek('-') || peek('+')) next(); }
    private void allowDigits() { while(peekDigit()) next(); }
    private void allowWhiteSpaceAndComments() { while (!done() && peekWhiteSpace() || peek('/')) {if (peek('/')) skipComment(); else i++;} }
    private void allowMinus() { if (!done() && peek('-')) next(); }
    private void allowDecimalAndDigits() { if (peek('.')) { next(); allowDigits(); } }

    protected Jtokenizer(String source) { src = source; }


    private double getNumber() {
        int start = i;
        allowMinus();
        requireZeroOrDigits();
        allowDecimalAndDigits();
        if (peekE())
            requireStandardForm();
        String number = src.substring(start, i);
        return Double.valueOf(number);
    }

    private String getUnquotedString() {
        allowWhiteSpaceAndComments();
        int start = i;
        while (!done() && isPermissibleNameChar(peek()))
            next();
        return src.substring(start, i);
    }

    private Json getUnknownAlphanumeric() {
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

    private char convertEscapedChar(char c) {
        switch (c) {
            case '"': return '"';
            case '\\': return '\\';
            case 'b': return '\b';
            case 'f': return '\f';
            case 'n': return '\n';
            case 'r': return '\r';
            case 't': return '\t';
            default: throw makeException("escapable char", c);
        }
    }

    private String convertedUnicodeString(String s) {
        int i = Integer.parseInt(s, 16);
        char[] c = Character.toChars(i);
        return new String(c);
    }

    private String getQuotedString() {
        allowWhiteSpaceAndComments();
        char c = requireQuote();
        int start = i;
        StringBuilder alternative = new StringBuilder();
        int lastStop = i;
        do {
            if (peek('\\')) {
                alternative.append(src.substring(lastStop, i));
                char escapedChar = requireEscapedChar();
                if (escapedChar == 'u') {
                    int hexStart = i;
                    requireFourHex();
                    alternative.append(convertedUnicodeString(src.substring(hexStart, i)));
                }
                else {
                    alternative.append(convertEscapedChar(escapedChar));
                }
                lastStop = i;
            }
        } while (next() != c);
        if (alternative.length() > 0) {
            alternative.append(src.substring(lastStop, i-1));
            return alternative.toString();
        }
        else {
            return src.substring(start, i-1);
        }
    }

    private String getString() {
        allowWhiteSpaceAndComments();
        if (peekQuote())
            return getQuotedString();
        else
            return getUnquotedString();
    }
}