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
    private String exceptionMessage(Jtoken expected, Jtoken actual) { return String.format(EXCEPTION, actual, expected, tokenizer.position()); }
    private JparserException makeException(Jtoken expected, Jtoken actual) { return new JparserException(exceptionMessage(expected, actual)); }


    public static Json stringToJson(String src) { return (new Jtparser(src)).getJson(); }

    public Jtparser(String src) { tokenizer = new Jtokenizer(src); }

    private void requireDone() { Jtoken t = tokenizer.nextToken(); if (!t.isEnd()) throw makeException(Jtoken.END, t);}

    protected Json getItem() {
        return null; // TODO
    }

    private Json getJson() {
        if (tokenizer == null)
            return null;
        Json item = getItem();
        requireDone();
        return item;
    }
}
