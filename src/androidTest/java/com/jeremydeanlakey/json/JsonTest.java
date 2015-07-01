package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;
//import com.jeremydeanlakey.json;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class JsonTest extends AndroidTestCase {
    public static String TEST_CASE_STRING =
            "{"
                    +   "\"emptyArray\":[],"
                    +    "\"nonEmptyArray\":[1],"
                    +    "\"string\": \"I am a string\","
                    +    "\"int\": 1,"
                    +    "\"long\": 10000000000,"
                    +    "\"double\": 1.0101010101010101,"
                    +    "\"null\": null,"
                    +    "\"boolean\": false,"
                    +    "\"emptyObject\": {},"
                    +    "\"nonEmptyObject\": {\"contents\":true}"
                    +"}";

    protected Json testJson;


    protected void setUp() {
        testJson = Json.fromString(TEST_CASE_STRING);
    }

    public void testNull() throws Throwable {
        setUp();
        Assert.assertFalse(testJson.isNull());
        Json nullJson = testJson.get("null");
        Assert.assertFalse(nullJson.isNull());
    }

    public void testBoolean() throws Throwable {
        // is true and false, get
    }

    public void testInt() throws Throwable {
        // is true and false, get
    }

    public void testLong() throws Throwable {
        // is true and false, get
    }

    public void testFloat() throws Throwable {
        // is true and false, get
    }

    public void testDouble() throws Throwable {
        // is true and false, get
    }

    public void testString() throws Throwable {
        // is true and false, get
    }

    public void testArray() throws Throwable {
        // is true and false, get, isEmpty, get index, hasNext, next, length
    }

    public void testObject() throws Throwable {
        // is true and false, get, isEmpty, get key, has, keys
    }

}
