package com.jeremydeanlakey.json;

import android.util.Log;
import android.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jeremydeanlakey on 5/01/16.
 */
public class Jtparser {
    private static final String TAG = "Jtparser";
    private static final String END = "END";
    private static final String NOT_END = "NOT END";
    private static final String EXCEPTION = "Unexpected character: '%s'.  Expected: '%s' at %d.";

    private Jtokenizer tokenizer;

    public class JparserException extends RuntimeException {
        JparserException(String message) { super(message); }
    }

    // TODO should tokenizer do previous position?
    private String exceptionMessage(String expected, Jtoken actual) { return String.format(EXCEPTION, actual, expected, tokenizer.position()); }
    private JparserException makeException(String expected, Jtoken actual) { return new JparserException(exceptionMessage(expected, actual)); }


    public static Json stringToJson(String src) { return (new Jtparser(src)).getJson(); }

    public Jtparser(String src) { tokenizer = new Jtokenizer(src); }

    // TODO implement equals in Jtoken
    private void require(Jtoken r) { Jtoken n = tokenizer.nextToken(); if (n != r) throw makeException(r.toString(), n); }
    private void require(String s) { Jtoken n = tokenizer.nextToken(); if (n.toString() != s) throw makeException(s, n); }

    private void requireDone() { Jtoken t = tokenizer.nextToken(); if (!t.isEnd()) throw makeException(Jtoken.END.toString(), t);}

    private Json getObject(){ return null; } // TODO
    private Json getArray(){ return null; } // TODO

    protected Json getItem() {
        Jtoken peek = tokenizer.peekToken();
        if (peek.isEnd()) throw makeException("not-empty string", Jtoken.END);
        if (peek.isObjectStart()) return getObject();
        if (peek.isArrayStart()) return getArray();
        if (peek.isStringValue()) return new Jstring(tokenizer.nextToken().getStringValue());
        if (peek.isNumber()) return new Jnumber(tokenizer.nextToken().getNumberValue());
        // if (peekAlphanumeric()) return getUnknownAlphanumeric();
        throw makeException("json value", peek);
    }

    private Json getJson() {
        if (tokenizer == null)
            return null;
        Json item = getItem();
        requireDone();
        return item;
    }
}
