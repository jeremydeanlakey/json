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

    public void testUnquotedString() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" test   ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals("test", token.getStringValue());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testNumber() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" -5.2   2.3e1  \t  5.2  -2e2");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isNumber());
        Assert.assertEquals(-5.2, token.getNumberValue());

        token = tokenizer.nextToken();
        Assert.assertTrue(token.isNumber());
        Assert.assertEquals(23.0, token.getNumberValue());

        token = tokenizer.nextToken();
        Assert.assertTrue(token.isNumber());
        Assert.assertEquals(5.2, token.getNumberValue());

        token = tokenizer.nextToken();
        Assert.assertTrue(token.isNumber());
        Assert.assertEquals(-200.0, token.getNumberValue());

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

    public void testArrayStart() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" [   ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isArrayStart());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testArrayEnd() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" ]  ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isArrayEnd());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testObjectStart() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" \n{  ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isObjectStart());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testObjectEnd() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" \n }  ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isObjectEnd());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testEnd() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" \n   ");
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testComment() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" // whatever \n }  ");
        Assert.assertTrue(tokenizer.nextToken().isObjectEnd());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testMultilineComment() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" /* test \n test */}  ");
        Assert.assertTrue(tokenizer.nextToken().isObjectEnd());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

    public void testEscapedCharacters() {
        Jtokenizer tokenizer = new Jtokenizer(" \"test\\ttest\"  \"\\\"\"  \"\\b\"  \"\\f\"  \"\\n\"  \"\\r\"  \"t\nt\"  ");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals(token.getStringValue(), "test\ttest");
        token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals(token.getStringValue(), "\"");
        token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals(token.getStringValue(), "\b");
        token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals(token.getStringValue(), "\f");
        token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals(token.getStringValue(), "\n");
        token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals(token.getStringValue(), "\r");
        token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals(token.getStringValue(), "t\nt");

        tokenizer = new Jtokenizer(" \"\\u1234\"  \"\\uffff\" \"\\u0000\" ");
        token = tokenizer.nextToken();
        Assert.assertTrue(token.isStringValue());
        Assert.assertEquals(token.getStringValue(), "\u1234");
        token = tokenizer.nextToken();
        Assert.assertEquals(token.getStringValue(), "\uffff");
        token = tokenizer.nextToken();
        Assert.assertEquals(token.getStringValue(), "\u0000");
    }
}
