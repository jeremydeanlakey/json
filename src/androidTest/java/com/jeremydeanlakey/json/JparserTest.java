package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class JparserTest extends AndroidTestCase {
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
        Jparser parser = new Jparser("{}");
        Json emptyObject = parser.getJobject();
    }

    public void testGetJarray() throws Throwable {
        Jparser parser = new Jparser("[]");
        Json emptyArray= parser.getJarray();
        parser = new Jparser("[1,]");
        Json nonEmptyArray= parser.getJarray();
        parser = new Jparser(nonEmptyArray.toString());
        Json copy = parser.getJarray();
        Assert.assertEquals(nonEmptyArray, copy);
    }

    public void testGetJobject() throws Throwable {
        // TODO
        Jparser parser = new Jparser("{}");
        Json emptyObject = parser.getJobject();

        parser = new Jparser("{'a': 1, \"b\":2,}");
        Json simpleObject = parser.getJobject();
        Assert.assertNotNull(simpleObject);
        Assert.assertTrue(simpleObject.has("a"));

    }

    public void testGetJstring() throws Throwable {
        // TODO
        Jparser parser = new Jparser("\"test\"");
        Json string = parser.getJstring();
         Assert.assertEquals(string.getString(), "test"); // expected:<["test"]> but was:<[test]>
    }

    public void testGetUnknownAlphanumeric() throws Throwable {
        // string
        Jparser parser = new Jparser("test ");
        Json string = parser.getUnknownAlphanumeric();
        Assert.assertEquals(string.getString(), "test");

        // null
        parser = new Jparser("null ");
        Json jnull = parser.getUnknownAlphanumeric();
        Assert.assertTrue(jnull.isNull());

        // boolean
        parser = new Jparser("true ");
        Json booleanTrue = parser.getUnknownAlphanumeric();
        Assert.assertEquals(booleanTrue.getBoolean(), true);

        // boolean
        parser = new Jparser("false ");
        Json booleanFalse = parser.getUnknownAlphanumeric();
        Assert.assertEquals(booleanFalse.getBoolean(), false);
    }

    public void testStringToJson() throws Throwable {
        Json test = Jparser.stringToJson(TEST_CASE_STRING);
        Json copy = Jparser.stringToJson(test.toString());
        Json emptyObject = Jparser.stringToJson("{}");
        Assert.assertEquals(test, copy);
        Assert.assertFalse(test.equals(emptyObject));
    }
    
    public void testComment() throws Throwable {
        Json withComment = Jparser.stringToJson("{'a':1, /**/}");
        Json copy = Jparser.stringToJson(withComment.toString());
        Assert.assertEquals(withComment, copy);

        withComment = Jparser.stringToJson("{'a':1, } //");
        copy = Jparser.stringToJson(withComment.toString());
        Assert.assertEquals(withComment, copy);
    }
}
