package com.jeremydeanlakey.json;

import android.util.Log;
import android.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jeremydeanlakey on 5/01/16.
 */
public class Jtparser {
    private Jtokenizer tokenizer;
    private Jtoken peek;

    public static Json stringToJson(String src) { return (new Jtparser(src)).getJson(); }

    public Jtparser(String src) { tokenizer = new Jtokenizer(src); }

    private Jtoken nextToken() {
        if (peek==null) {
            return tokenizer.nextToken();
        } else {
            Jtoken answer = peek;
            peek = null;
            return peek;
        }
    }

    private void requireDone() {  } // TODO

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
