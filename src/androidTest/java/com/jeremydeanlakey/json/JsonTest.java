package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;
import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class JsonTest extends AndroidTestCase {
    public static String TEST_CASE_STRING =
            "{"
        +    "\"emptyArray\":[],"
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
        +        "\"object\":{\"key\":1}"
        +      "}"
        +    "}";

    protected Json testJson = Json.fromString(TEST_CASE_STRING);

    private Json EMPTY_ARRAY, NULL, EMPTY_OBJECT, ONE, STRING, DIFFERENT_STRING, TRUE, FALSE, NON_EMPTY_ARRAY;

    public void setUp() {
        JSONArray ja = null, jna = null;
        JSONObject jo = null;
        try {
            ja = new JSONArray("[]");
            jna = new JSONArray("[{\"contents\": 1}, 2, false, null, \"string\", []]");
            jo = new JSONObject("{}");
        } catch (Exception e) {}
        EMPTY_ARRAY = Json.fromJsonArray(ja);
        NON_EMPTY_ARRAY = Json.fromJsonArray(jna);
        EMPTY_OBJECT = Json.fromJsonObject(jo);
        NULL = new Jnull();
        ONE = new Jnumber(1);
        STRING = new Jstring("I am a string");
        DIFFERENT_STRING = new Jstring("different string");
        TRUE = new Jboolean(true);
        FALSE = new Jboolean(false);
    }

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
        Assert.assertTrue(target.equals(NULL));
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
        Assert.assertEquals(target, FALSE);
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
        Json one = numbersArray.get(3);
        Json two = numbersArray.get(4);
        Assert.assertTrue(one.equals(ONE));
        Assert.assertFalse(two.equals(ONE));
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
        Json string = testJson.get("string");
        Assert.assertTrue(string.equals(STRING));
        Assert.assertFalse(string.equals(DIFFERENT_STRING));
        Assert.assertFalse(string.equals(ONE));
        Assert.assertFalse(string.equals(EMPTY_ARRAY));
        Assert.assertFalse(string.equals(EMPTY_OBJECT));
        Assert.assertFalse(string.equals(NULL));
        Assert.assertFalse(string.equals(FALSE));
    }

    public void testArray() throws Throwable {
        Assert.assertFalse(testJson.isNull());
        Json emptyArray = testJson.get("emptyArray");
        Json nonEmptyArray = testJson.get("nonEmptyArray");
        Assert.assertNotNull(emptyArray);
        Assert.assertNotNull(nonEmptyArray);

        Assert.assertNotSame(emptyArray, nonEmptyArray);
        Assert.assertTrue(emptyArray.equals(EMPTY_ARRAY));
        Assert.assertFalse(nonEmptyArray.equals(EMPTY_ARRAY));
        Assert.assertTrue(nonEmptyArray.equals(NON_EMPTY_ARRAY));
        Assert.assertFalse(emptyArray.equals(NON_EMPTY_ARRAY));

        Assert.assertFalse(emptyArray.iterator().hasNext());
        Assert.assertTrue(nonEmptyArray.iterator().hasNext());

        Assert.assertFalse(emptyArray.isNull());
        Assert.assertFalse(emptyArray.isBoolean());
        Assert.assertFalse(emptyArray.isNumber());
        Assert.assertFalse(emptyArray.isString());
        Assert.assertTrue(emptyArray.isArray());
        Assert.assertFalse(emptyArray.isObject());

        Assert.assertTrue(nonEmptyArray.isArray());
        Assert.assertFalse(nonEmptyArray.isEmpty());
        Assert.assertEquals(nonEmptyArray.length(), 6);

        // "nonEmptyArray":[{"contents": 1}, 2, false, null, "string", []]
        Assert.assertTrue(nonEmptyArray.hasObject(0));
        Assert.assertFalse(nonEmptyArray.hasObject(1));
        Assert.assertTrue(nonEmptyArray.hasLong(1));
        Assert.assertFalse(nonEmptyArray.hasLong(0));
        Assert.assertTrue(nonEmptyArray.hasDouble(1));
        Assert.assertFalse(nonEmptyArray.hasDouble(0));
        Assert.assertTrue(nonEmptyArray.hasBoolean(2));
        Assert.assertFalse(nonEmptyArray.hasBoolean(1));
        Assert.assertTrue(nonEmptyArray.hasNull(3));
        Assert.assertFalse(nonEmptyArray.hasNull(2));
        Assert.assertTrue(nonEmptyArray.hasString(4));
        Assert.assertFalse(nonEmptyArray.hasString(3));
        Assert.assertTrue(nonEmptyArray.hasArray(5));
        Assert.assertFalse(nonEmptyArray.hasArray(4));

        // TODO getX(i) tests

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
        Json nonEmptyObject = testJson.get("nonEmptyObject");
        Assert.assertNotNull(nonEmptyObject);

        Assert.assertTrue(emptyObject.isObject());
        Assert.assertTrue(nonEmptyObject.isObject());
        Assert.assertTrue(emptyObject.isEmpty());
        Assert.assertFalse(nonEmptyObject.isEmpty());

        Assert.assertFalse(emptyObject.isNull());
        Assert.assertFalse(emptyObject.isBoolean());
        Assert.assertFalse(emptyObject.isNumber());
        Assert.assertFalse(emptyObject.isString());
        Assert.assertFalse(emptyObject.isArray());


        Json testObject = testJson.get("testObject");
        Assert.assertTrue(testObject.isDouble("number"));
        Assert.assertTrue(testObject.hasDouble("number"));
        Assert.assertEquals(testObject.getDouble("number"), 1.0);
        Assert.assertEquals(testObject.getDouble("number", 2.0), 1.0);
        Assert.assertFalse(testObject.isDouble("string"));
        Assert.assertFalse(testObject.hasDouble("string"));
        Assert.assertFalse(testObject.hasDouble("nothing"));
        Assert.assertTrue(testObject.getDouble("number") != 2.0);
        Assert.assertEquals(testObject.getDouble("string", 2.0), 2.0);
        Assert.assertEquals(testObject.getDouble("nothing", 2.0), 2.0);
        // TODO test getObjectOrDefault, getArrayOrDefault functions

        Assert.assertTrue(testObject.isLong("number"));
        Assert.assertTrue(testObject.hasLong("number"));
        Assert.assertEquals(testObject.getLong("number"), 1);
        Assert.assertEquals(testObject.getLong("number", 2), 1);
        Assert.assertFalse(testObject.isLong("string"));
        Assert.assertFalse(testObject.hasLong("string"));
        Assert.assertFalse(testObject.hasLong("nothing"));
        Assert.assertTrue(testObject.getLong("number") != 2);
        Assert.assertEquals(testObject.getLong("string", 2), 2);
        Assert.assertEquals(testObject.getLong("nothing", 2), 2);

        Assert.assertTrue(testObject.isString("string"));
        Assert.assertTrue(testObject.hasString("string"));
        Assert.assertEquals(testObject.getString("string"), "string");
        Assert.assertEquals(testObject.getString("string", "nothing"), "string");
        Assert.assertFalse(testObject.isString("number"));
        Assert.assertFalse(testObject.hasString("number"));
        Assert.assertFalse(testObject.hasString("nothing"));
        Assert.assertFalse(testObject.getString("string").equals("blah"));
        Assert.assertEquals(testObject.getString("number", "nothing"), "nothing");
        Assert.assertEquals(testObject.getString("nothing", "nothing"), "nothing");


        Assert.assertTrue(testObject.isBoolean("bool"));
        Assert.assertTrue(testObject.hasBoolean("bool"));
        Assert.assertEquals(testObject.getBoolean("bool"), true);
        Assert.assertEquals(testObject.getBoolean("bool", false), true);
        Assert.assertFalse(testObject.isBoolean("number"));
        Assert.assertFalse(testObject.hasBoolean("number"));
        Assert.assertEquals(testObject.getBoolean("number", false), false);
        Assert.assertEquals(testObject.getBoolean("nothing", false), false);

        Assert.assertTrue(testObject.isNull("null"));
        Assert.assertTrue(testObject.hasNull("null"));
        Assert.assertFalse(testObject.isNull("string"));
        Assert.assertFalse(testObject.hasNull("string"));
        Assert.assertTrue(testObject.isArray("array"));
        Assert.assertTrue(testObject.hasArray("array"));
        Assert.assertTrue(testObject.isObject("object"));
        Assert.assertTrue(testObject.hasObject("object"));

        Set<String> keys = nonEmptyObject.keys();
        Assert.assertEquals(keys.size(), 1);
        Assert.assertTrue(keys.contains("contents"));
        Assert.assertTrue(nonEmptyObject.has("contents"));
        Json objectContents = nonEmptyObject.get("contents");
        Assert.assertNotNull(objectContents);
        Assert.assertTrue(objectContents.isNumber());
        Assert.assertEquals(objectContents.getLong(), 1);

        // TODO test string functions after they are improved on
    }
}
