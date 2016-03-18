package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;
import junit.framework.Assert;

/**
 * Created by jeremydeanlakey on 3/14/16.
 */
public class JtokenizerTest extends AndroidTestCase {

    public void setUp() {
    }

    public void testString() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" \"test\"   ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals("test", token.getStringValue());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testNumber() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" -5.2   ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isNumber());
        Assert.assertEquals(-5.2, token.getNumberValue());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testColon() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" :   ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isColon());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testComma() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" ,   ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isComma());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

}
