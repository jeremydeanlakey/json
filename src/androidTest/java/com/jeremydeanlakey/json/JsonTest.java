package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;
//import com.jeremydeanlakey.json;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class JsonTest extends AndroidTestCase {
    public static String TEST_CASE_STRING =
            "{"
                    +   "\"emptyArray\":[],"
                    +    "\"nonEmptyArray\":[{\"contents\": 1}],"
                    +    "\"string\": \"I am a string\","
                    +    "\"int\": 1,"
                    +    "\"long\": 10000000000L,"
                    +    "\"float\": 1.0101010101010101,"
                    +    "\"double\": 1.0101010101010101,"
                    +    "\"null\": null,"
                    +    "\"boolean\": false,"
                    +    "\"emptyObject\": {},"
                    +    "\"nonEmptyObject\": {\"contents\":1}"
                    +"}";

    protected Json testJson = Json.fromString(TEST_CASE_STRING);


    public void testNull() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("null");
        Assert.assertNotNull(target);
        Assert.assertTrue(target.isNull());
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
/*
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
*/
    public void testFloat() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("float");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        //Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        //Assert.assertTrue(target.isFloat());
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
        //Assert.assertFalse(target.isInt());
        Assert.assertFalse(target.isLong());
        Assert.assertFalse(target.isFloat());
        //Assert.assertTrue(target.isDouble());
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
        //Assert.assertTrue(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertEquals(target.getString(), "I am a string");
    }

    public void testArray() throws Throwable {
        // is true and false, get, isEmpty, get index, hasNext, next, length
        Assert.assertFalse(testJson.isNull());
        Json emptyArray = testJson.get("emptyArray");
        Assert.assertNotNull(emptyArray);
        Assert.assertFalse(emptyArray.isNull());
        Assert.assertFalse(emptyArray.isBoolean());
        Assert.assertFalse(emptyArray.isInt());
        Assert.assertFalse(emptyArray.isLong());
        Assert.assertFalse(emptyArray.isFloat());
        Assert.assertFalse(emptyArray.isDouble());
        Assert.assertFalse(emptyArray.isString());
        //Assert.assertTrue(target.isArray());
        Assert.assertFalse(emptyArray.isObject());


        Json nonEmptyArray = testJson.get("nonEmptyArray");
        Assert.assertNotNull(nonEmptyArray);
        Assert.assertTrue(nonEmptyArray.isArray());
        Assert.assertFalse(nonEmptyArray.isEmpty());
        Assert.assertEquals(nonEmptyArray.length(), 1);

        Json objectInArray = nonEmptyArray.get(0);
        Assert.assertNotNull(objectInArray);
        Assert.assertTrue(objectInArray.isObject());
        Assert.assertTrue(objectInArray.has("contents"));
        Json objectContents = objectInArray.get("contents");
        Assert.assertNotNull(objectContents);
        Assert.assertTrue(objectContents.isInt());
        Assert.assertEquals(objectContents.getInt(), 1);
    }

    public void testObject() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json emptyObject = testJson.get("emptyObject");
        Assert.assertNotNull(emptyObject);
        Assert.assertTrue(emptyObject.isEmpty());
        Assert.assertFalse(emptyObject.isNull());
        Assert.assertFalse(emptyObject.isBoolean());
        Assert.assertFalse(emptyObject.isInt());
        Assert.assertFalse(emptyObject.isLong());
        Assert.assertFalse(emptyObject.isFloat());
        Assert.assertFalse(emptyObject.isDouble());
        Assert.assertFalse(emptyObject.isString());
        Assert.assertFalse(emptyObject.isArray());
        Assert.assertTrue(emptyObject.isObject());

        Json nonEmptyObject = testJson.get("nonEmptyObject");
        Assert.assertNotNull(nonEmptyObject);
        Assert.assertTrue(nonEmptyObject.isObject());
        Assert.assertFalse(nonEmptyObject.isEmpty());
        Set<String> keys = nonEmptyObject.keys();
        Assert.assertEquals(keys.size(), 1);
        Assert.assertTrue(keys.contains("contents"));
        Assert.assertTrue(nonEmptyObject.has("contents"));
        Json objectContents = nonEmptyObject.get("contents");
        Assert.assertNotNull(objectContents);
        Assert.assertTrue(objectContents.isInt());
        Assert.assertEquals(objectContents.getInt(), 1);
    }
}
