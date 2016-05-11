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

        // isValidToken(c)
        Assert.assertEquals(token.toString(), "5.3");
        Assert.assertEquals(token.getNumberValue(), 5.3);
        // Assert.assertEquals(token.getStringValue(), whatever);
        // Assert.assertEquals(token.getJsonValue(), whatever);
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

        // isValidToken(c)
        Assert.assertEquals(token.toString(), "I'm a string!");
        //Assert.assertEquals(token.getNumberValue(), 5.3);
        Assert.assertEquals(token.getStringValue(), "I'm a string!");
        // Assert.assertEquals(token.getJsonValue(), whatever);
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
        // Assert.assertEquals(token.toString(), "I'm a string!");
        //Assert.assertEquals(token.getNumberValue(), 5.3);
        //Assert.assertEquals(token.getStringValue(), "I'm a string!");
        // Assert.assertEquals(token.getJsonValue(), whatever);
    }

    public void testObjectEnd() throws Throwable {
        Jtokenizer tokenizer = new Jtokenizer(" }   ");
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

        Assert.assertTrue(Jtoken.isValidToken('}'));
        // Assert.assertEquals(token.toString(), "I'm a string!");
        //Assert.assertEquals(token.getNumberValue(), 5.3);
        //Assert.assertEquals(token.getStringValue(), "I'm a string!");
        // Assert.assertEquals(token.getJsonValue(), whatever);
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
        // Assert.assertEquals(token.toString(), "I'm a string!");
        //Assert.assertEquals(token.getNumberValue(), 5.3);
        //Assert.assertEquals(token.getStringValue(), "I'm a string!");
        // Assert.assertEquals(token.getJsonValue(), whatever);
    }

    // TODO: json
    // TODO: :
    // TODO: ]
    // TODO: ,
    // TODO: end

}
