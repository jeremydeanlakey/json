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

    protected Json testJson = Json.fromString(TEST_CASE_STRING);


    public void testNull() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("null");
        Assert.assertNotNull(target);
        // TODO
        // Assert.assertTrue(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertFalse(target.isFloat());
        Assert.assertFalse(target.isDouble());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
    }

    public void testBoolean() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("boolean");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertTrue(target.isBoolean());
        Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertFalse(target.isFloat());
        Assert.assertFalse(target.isDouble());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertEquals(target.getBoolean(), false);
    }

    public void testInt() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("int");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertTrue(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertFalse(target.isFloat());
        Assert.assertFalse(target.isDouble());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertEquals(target.getInt(), 1);
    }

    public void testLong() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("long");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isInt());
        Assert.assertTrue(target.isLong());
        Assert.assertFalse(target.isFloat());
        Assert.assertFalse(target.isDouble());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertEquals(target.getLong(), 10000000000L);
    }

    public void testFloat() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("float");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertTrue(target.isFloat());
        Assert.assertFalse(target.isDouble());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
    }

    public void testDouble() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("double");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertFalse(target.isFloat());
        Assert.assertTrue(target.isDouble());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
    }

    public void testString() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("string");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertFalse(target.isFloat());
        Assert.assertFalse(target.isDouble());
        Assert.assertTrue(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertEquals(target.getString(), "I am a string");
    }

    public void testArray() throws Throwable {
        // is true and false, get, isEmpty, get index, hasNext, next, length
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("array");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertFalse(target.isFloat());
        Assert.assertFalse(target.isDouble());
        Assert.assertFalse(target.isString());
        Assert.assertTrue(target.isArray());
        Assert.assertFalse(target.isObject());
    }

    public void testObject() throws Throwable {
        // is true and false, get, isEmpty, get key, has, keys
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("object");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertFalse(target.isFloat());
        Assert.assertFalse(target.isDouble());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertTrue(target.isObject());
    }

}
