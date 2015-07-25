package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;
import junit.framework.Assert;
import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class JsonTest extends AndroidTestCase {
    public static String TEST_CASE_STRING =
            "{"
        +    "\"emptyArray\":[],"
        +    "\"nonEmptyArray\":[{\"contents\": 1}],"
        +    "\"string\": \"I am a string\","
        +    "\"numbers\":[-2e0, -1.000, 0e10, 10e-1, 2],"
        +    "\"bigNumber\": " + Integer.MAX_VALUE + ","
        +    "\"null\": null,"
        +    "\"boolean\": false,"
        +    "\"emptyObject\": {},"
        +    "\"nonEmptyObject\": {\"contents\":1},"
        +    "\"testObject\": {"
        +        "\"number\":1,"
        +        "\"string\":\"string\","
        +        "\"bool\":\"true\","
        +        "\"null\":null,"
        +        "\"array\":[],"
        +        "\"object\":{\"key\":1}"
        +      "}"
        +    "}";

    protected Json testJson = Json.fromString(TEST_CASE_STRING);


    public void testNull() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("null");
        Assert.assertNotNull(target);
        Assert.assertTrue(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isNumber());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertTrue(testJson.isNull("null"));
    }

    public void testBoolean() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("boolean");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertTrue(target.isBoolean());
        Assert.assertFalse(target.isNumber());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertEquals(target.getBoolean(), false);
        Assert.assertEquals(testJson.getBoolean("boolean"), target.getBoolean());
    }

    public void testNumber() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("bigNumber");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertTrue(target.isNumber());
        Assert.assertFalse(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertEquals(target.getLong(), Integer.MAX_VALUE);
        Assert.assertEquals(testJson.getLong("bigNumber"), target.getLong());

        Json numbersArray = testJson.get("numbers");
        Assert.assertNotNull(numbersArray);
        Assert.assertFalse(numbersArray.isNull());
        Assert.assertTrue(numbersArray.isArray());
        Assert.assertEquals(numbersArray.length(), 5);
        Assert.assertEquals(numbersArray.getLong(0), -2);
        Assert.assertEquals(numbersArray.getLong(1), -1);
        Assert.assertEquals(numbersArray.getLong(2), 0);
        Assert.assertEquals(numbersArray.getLong(3), 1);
        Assert.assertEquals(numbersArray.getLong(4), 2);
    }

    public void testString() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json target = testJson.get("string");
        Assert.assertNotNull(target);
        Assert.assertFalse(target.isNull());
        Assert.assertFalse(target.isBoolean());
        Assert.assertFalse(target.isNumber());
        Assert.assertTrue(target.isString());
        Assert.assertFalse(target.isArray());
        Assert.assertFalse(target.isObject());
        Assert.assertEquals(target.getString(), "I am a string");
        Assert.assertEquals(target.getString(), testJson.getString("string"));
    }

    public void testArray() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json emptyArray = testJson.get("emptyArray");
        Assert.assertNotNull(emptyArray);
        Assert.assertFalse(emptyArray.isNull());
        Assert.assertFalse(emptyArray.isBoolean());
        Assert.assertFalse(emptyArray.isNumber());
        Assert.assertFalse(emptyArray.isString());
        Assert.assertTrue(emptyArray.isArray());
        Assert.assertFalse(emptyArray.isObject());
        Assert.assertFalse(emptyArray.iterator().hasNext());

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
        Assert.assertTrue(objectContents.isNumber());
        Assert.assertEquals(objectContents.getLong(), 1);
    }

    public void testObject() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json emptyObject = testJson.get("emptyObject");
        Assert.assertNotNull(emptyObject);
        Assert.assertTrue(emptyObject.isEmpty());
        Assert.assertFalse(emptyObject.isNull());
        Assert.assertFalse(emptyObject.isBoolean());
        Assert.assertFalse(emptyObject.isNumber());
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
        Assert.assertTrue(objectContents.isNumber());
        Assert.assertEquals(objectContents.getLong(), 1);

        Json testObject = testJson.get("testObject");
        Assert.assertTrue(testObject.hasDouble("number"));
        Assert.assertEquals(testObject.getDouble("number"),1.0);
        Assert.assertTrue(testObject.hasLong("number"));
        Assert.assertEquals(testObject.getLong("number"),1);
        Assert.assertTrue(testObject.hasString("string"));
        Assert.assertEquals(testObject.getString("string"),"string");
        Assert.assertTrue(testObject.hasBoolean("bool"));
        Assert.assertEquals(testObject.getBoolean("bool"),true);
        Assert.assertTrue(testObject.hasNull("null"));
        // Assert.assertTrue(testObject.hasArray("array"));
        // Assert.assertTrue(testObject.hasObject("object"));


        // TODO test isXxxx(key) functions
        // TODO test getXxxxOrDefault functions
        // TODO test string functions after they are improved on
    }
}
