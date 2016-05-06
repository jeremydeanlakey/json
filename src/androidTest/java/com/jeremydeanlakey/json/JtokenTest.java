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
        // toString()
        // getNumberValue());
        // getStringValue());
        // getJsonValue());
    }
    // TODO: number
    // TODO: string
    // TODO: json
    // TODO: {
    // TODO: :
    // TODO: }
    // TODO: [
    // TODO: ]
    // TODO: ,
    // TODO: end

}
