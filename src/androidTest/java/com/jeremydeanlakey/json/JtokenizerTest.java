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
}
