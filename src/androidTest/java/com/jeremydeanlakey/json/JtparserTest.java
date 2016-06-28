package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class JtparserTest extends AndroidTestCase {
    public static String TEST_CASE_STRING =
            "{"
        +    "\"emptyArray\":[true],"
        +    "\"nonEmptyArray\":[{\"contents\": 1}, 2, false, null, \"string\", []],"
        +    "\"string\": \"I am a string\","
        +    "\"numbers\":[-2e0, -1.000, 0e10, 10e-1, 2],"
        +    "\"bigNumber\": " + Integer.MAX_VALUE + ","
        +    "\"null\": null,"
        +    "\"boolean\": false,"
        +    "\"emptyObject\": {},"
        +    "\"notObject\": 1,"
        +    "\"nonEmptyObject\": {\"contents\":1},"
        +    "\"testObject\": {"
        +        "\"number\":1,"
        +        "\"string\":\"string\","
        +        "\"bool\":\"true\","
        +        "\"null\":null,"
        +        "\"array\":[],"
        +        "\"object\":{}"
        +      "}"
        +    "}";


    public void testGetItem() throws Throwable {
        Jtparser parser = new Jtparser("{}");
        Json emptyObject = parser.getObject();
    }

    public void testGetJarray() throws Throwable {
        Jtparser parser = new Jtparser("[]");
        Json emptyArray= parser.getArray();
        parser = new Jtparser("[1,]");
        Json nonEmptyArray= parser.getArray();
        parser = new Jtparser(nonEmptyArray.toString());
        Json copy = parser.getArray();
        Assert.assertEquals(nonEmptyArray, copy);
    }

    public void testGetJobject() throws Throwable {
        Jtparser parser = new Jtparser("{}");
        Json emptyObject = parser.getObject();

        parser = new Jtparser("{'a': 1, \"b\":2,}");
        Json simpleObject = parser.getObject();
        Assert.assertNotNull(simpleObject);
        Assert.assertTrue(simpleObject.has("a"));
        parser = new Jtparser(simpleObject.toString());
        Json copy = parser.getObject();
        Assert.assertEquals(simpleObject, copy);
    }

    public void testGetJstring() throws Throwable {
        Jtparser parser = new Jtparser("\"test\"");
        Json string = parser.getItem();
         Assert.assertEquals(string.getString(), "test"); // expected:<["test"]> but was:<[test]>
    }

    public void testStringToJson() throws Throwable {
        Json test = Jtparser.stringToJson(TEST_CASE_STRING);
        Json copy = Jtparser.stringToJson(test.toString());
        Json emptyObject = Jtparser.stringToJson("{}");
        Assert.assertEquals(test, copy);
        Assert.assertFalse(test.equals(emptyObject));

        Json messyObject = Jtparser.stringToJson("{/*comment*/a:1 } // ");
        Assert.assertEquals(messyObject.getLong("a"), 1);
    }
}
