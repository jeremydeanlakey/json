package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Created by jeremydeanlakey on 4/5/16.
 */
public class JtokenTest extends AndroidTestCase {

    public void setUp() {
    }

    public void testNumber() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" 5.3   ");
        Jtoken token = tokenizer.nextToken();

        Assert.assertTrue(token.isNumber());
        Assert.assertFalse(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertFalse(token.isObjectStart());
        Assert.assertFalse(token.isObjectEnd());
        Assert.assertFalse(token.isColon());
        Assert.assertFalse(token.isArrayStart());
        Assert.assertFalse(token.isArrayEnd());
        Assert.assertFalse(token.isComma());
        Assert.assertFalse(token.isEnd());

        Assert.assertEquals(token.toString(), "5.3");
        Assert.assertEquals(token.getNumberValue(), 5.3);
    }

    public void testString() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" \"I'm a string!\"   ");
        Jtoken token = tokenizer.nextToken();

        Assert.assertFalse(token.isNumber());
        Assert.assertTrue(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertFalse(token.isObjectStart());
        Assert.assertFalse(token.isObjectEnd());
        Assert.assertFalse(token.isColon());
        Assert.assertFalse(token.isArrayStart());
        Assert.assertFalse(token.isArrayEnd());
        Assert.assertFalse(token.isComma());
        Assert.assertFalse(token.isEnd());

        Assert.assertEquals(token.toString(), "I'm a string!");
        Assert.assertEquals(token.getStringValue(), "I'm a string!");
    }

    public void testObjectStart() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" {   ");
        Jtoken token = tokenizer.nextToken();

        Assert.assertFalse(token.isNumber());
        Assert.assertFalse(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertTrue(token.isObjectStart());
        Assert.assertFalse(token.isObjectEnd());
        Assert.assertFalse(token.isColon());
        Assert.assertFalse(token.isArrayStart());
        Assert.assertFalse(token.isArrayEnd());
        Assert.assertFalse(token.isComma());
        Assert.assertFalse(token.isEnd());

        Assert.assertTrue(Jtoken.isValidToken('{'));
        Assert.assertEquals(token.toString(), "{");
    }

    public void testObjectEnd() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" }   ");
        Jtoken token = tokenizer.nextToken();

        Assert.assertFalse(token.isNumber());
        Assert.assertFalse(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertFalse(token.isObjectStart());
        Assert.assertTrue(token.isObjectEnd());
        Assert.assertFalse(token.isColon());
        Assert.assertFalse(token.isArrayStart());
        Assert.assertFalse(token.isArrayEnd());
        Assert.assertFalse(token.isComma());
        Assert.assertFalse(token.isEnd());

        Assert.assertTrue(Jtoken.isValidToken('}'));
        Assert.assertEquals(token.toString(), "}");
    }

    public void testArrayStart() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" [   ");
        Jtoken token = tokenizer.nextToken();

        Assert.assertFalse(token.isNumber());
        Assert.assertFalse(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertFalse(token.isObjectStart());
        Assert.assertFalse(token.isObjectEnd());
        Assert.assertFalse(token.isColon());
        Assert.assertTrue(token.isArrayStart());
        Assert.assertFalse(token.isArrayEnd());
        Assert.assertFalse(token.isComma());
        Assert.assertFalse(token.isEnd());

        Assert.assertTrue(Jtoken.isValidToken('['));
        Assert.assertEquals(token.toString(), "[");
    }

    public void testArrayEnd() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" ]   ");
        Jtoken token = tokenizer.nextToken();

        Assert.assertFalse(token.isNumber());
        Assert.assertFalse(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertFalse(token.isObjectStart());
        Assert.assertFalse(token.isObjectEnd());
        Assert.assertFalse(token.isColon());
        Assert.assertFalse(token.isArrayStart());
        Assert.assertTrue(token.isArrayEnd());
        Assert.assertFalse(token.isComma());
        Assert.assertFalse(token.isEnd());

        Assert.assertTrue(Jtoken.isValidToken(']'));
        Assert.assertEquals(token.toString(), "]");
    }

    public void testComma() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" ,   ");
        Jtoken token = tokenizer.nextToken();

        Assert.assertFalse(token.isNumber());
        Assert.assertFalse(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertFalse(token.isObjectStart());
        Assert.assertFalse(token.isObjectEnd());
        Assert.assertFalse(token.isColon());
        Assert.assertFalse(token.isArrayStart());
        Assert.assertFalse(token.isArrayEnd());
        Assert.assertTrue(token.isComma());
        Assert.assertFalse(token.isEnd());

        Assert.assertTrue(Jtoken.isValidToken(','));
        Assert.assertEquals(token.toString(), ",");
    }

    public void testColon() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" :   ");
        Jtoken token = tokenizer.nextToken();

        Assert.assertFalse(token.isNumber());
        Assert.assertFalse(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertFalse(token.isObjectStart());
        Assert.assertFalse(token.isObjectEnd());
        Assert.assertTrue(token.isColon());
        Assert.assertFalse(token.isArrayStart());
        Assert.assertFalse(token.isArrayEnd());
        Assert.assertFalse(token.isComma());
        Assert.assertFalse(token.isEnd());

        Assert.assertTrue(Jtoken.isValidToken(':'));
        Assert.assertEquals(token.toString(), ":");
    }

    public void testEnd() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer("");
        Jtoken token = tokenizer.nextToken();

        Assert.assertFalse(token.isNumber());
        Assert.assertFalse(token.isStringValue());
        Assert.assertFalse(token.isJsonValue());
        Assert.assertFalse(token.isObjectStart());
        Assert.assertFalse(token.isObjectEnd());
        Assert.assertFalse(token.isColon());
        Assert.assertFalse(token.isArrayStart());
        Assert.assertFalse(token.isArrayEnd());
        Assert.assertFalse(token.isComma());
        Assert.assertTrue(token.isEnd());

        Assert.assertEquals(token.toString(), Jtoken.END);
    }
}
