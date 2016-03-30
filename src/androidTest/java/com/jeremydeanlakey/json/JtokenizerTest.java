package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;
import junit.framework.Assert;

/**
 * Created by jeremydeanlakey on 3/14/16.
 */
public class JtokenizerTest extends AndroidTestCase {
    // TODO: test hex, single line comment, multiline comment

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
        Jtokenizer tokenizer = new Jtokenizer(" -5.2   2.3e1");
        Jtoken token = tokenizer.nextToken();
        Assert.assertTrue(token.isNumber());
        Assert.assertEquals(-5.2, token.getNumberValue());

        token = tokenizer.nextToken();
        Assert.assertTrue(token.isNumber());
        Assert.assertEquals(23.0, token.getNumberValue());
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
        Jtokenizer tokenizer = new Jtokenizer(" /* test \n test *\\}  ");
        Assert.assertTrue(tokenizer.nextToken().isObjectEnd());
        Assert.assertTrue(tokenizer.nextToken().isEnd());
    }

}
