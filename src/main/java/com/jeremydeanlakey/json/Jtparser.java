package com.jeremydeanlakey.json;

import android.util.Pair;

/**
 * Created by jeremydeanlakey on 5/01/16.
 */
public class Jtparser {
    protected static final String TAG = "Jtparser";
    private static final String EXCEPTION = "Unexpected character: '%s'.  Expected: '%s' at %d.";

    private Jtokenizer tokenizer;

    public class JparserException extends RuntimeException {
        JparserException(String message) { super(message); }
    }

    private String exceptionMessage(String expected, Jtoken actual) { return String.format(EXCEPTION, actual, expected, tokenizer.position()); }
    private JparserException makeException(String expected) { return new JparserException(exceptionMessage(expected, peek())); }


    public static Json stringToJson(String src) { return (new Jtparser(src)).getJson(); }

    public Jtparser(String src) { tokenizer = new Jtokenizer(src); }

    private Jtoken next() { return tokenizer.nextToken(); }
    private Jtoken peek() { return tokenizer.peekToken(); }

    private void require(String s) { if (!peek().toString().equals(s)) throw makeException(s); next(); }

    private void requireDone() { Jtoken t = next(); if (!t.isEnd()) throw makeException(Jtoken.END.toString());}
    private void requireArrayStart() { require("["); }
    private void requireArrayEnd() { require("]"); }
    private void requireObjectStart() { require("{"); }
    private void requireObjectEnd() { require("}"); }
    private void requireComma() { require(","); }
    private void requireColon() { require(":"); }
    private String requireString() { if (!peek().isStringValue()) throw makeException("string"); return next().getStringValue(); }

    private Pair<String, Json> getKeyValue() {
        String key = requireString();
        requireColon();
        Json value = getItem();
        return new Pair(key, value);
    }

    protected Json getJobject() {
        requireObjectStart();
        Jobject object = new Jobject();
        if (tokenizer.peekToken().isObjectEnd()) {
            requireObjectEnd();
            return object;
        }
        Pair<String, Json> keyValue = getKeyValue();
        while (keyValue != null) {
            object.put(keyValue.first, keyValue.second);
            if (tokenizer.peekToken().isObjectEnd()) break;
            requireComma();
            if (tokenizer.peekToken().isObjectEnd()) break;
            keyValue = getKeyValue();
        }
        requireObjectEnd();
        return object;
    }

    protected Json getJarray(){
        Jarray array = new Jarray();
        requireArrayStart();
        while (!tokenizer.peekToken().isArrayEnd()) {
            array.add(getItem());
            if (tokenizer.peekToken().isArrayEnd())
                break;
            requireComma();
        }
        requireArrayEnd();
        return array;
    }

    protected Json getItem() {
        Jtoken peek = tokenizer.peekToken();
        if (peek.isEnd()) throw makeException("not-empty string");
        if (peek.isObjectStart()) return getJobject();
        if (peek.isArrayStart()) return getJarray();
        if (peek.isStringValue()) return new Jstring(next().getStringValue());
        if (peek.isNumber()) return new Jnumber(next().getNumberValue());
        throw makeException("json value");
    }

    private Json getJson() {
        if (tokenizer == null)
            return null;
        Json item = getItem();
        requireDone();
        return item;
    }
}
