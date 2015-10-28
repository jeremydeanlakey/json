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
        +        "\"object\":{}"
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
        Assert.assertFalse(target.equals(STRING));
        Assert.assertFalse(target.equals(ONE));
        Assert.assertFalse(target.equals(EMPTY_ARRAY));
        Assert.assertFalse(target.equals(EMPTY_OBJECT));
        Assert.assertFalse(target.equals(FALSE));
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

        Assert.assertTrue(target.equals(FALSE));
        Assert.assertFalse(target.equals(NULL));
        Assert.assertFalse(target.equals(STRING));
        Assert.assertFalse(target.equals(ONE));
        Assert.assertFalse(target.equals(EMPTY_ARRAY));
        Assert.assertFalse(target.equals(EMPTY_OBJECT));
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
        Assert.assertFalse(one.equals(FALSE));
        Assert.assertFalse(one.equals(NULL));
        Assert.assertFalse(one.equals(STRING));
        Assert.assertFalse(one.equals(EMPTY_ARRAY));
        Assert.assertFalse(one.equals(EMPTY_OBJECT));
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
        Assert.assertEquals(nonEmptyArray.getLong(1), 2);
        Assert.assertTrue(nonEmptyArray.hasDouble(1));
        Assert.assertFalse(nonEmptyArray.hasDouble(0));
        Assert.assertEquals(nonEmptyArray.getDouble(1), 2.);
        Assert.assertTrue(nonEmptyArray.hasBoolean(2));
        Assert.assertFalse(nonEmptyArray.hasBoolean(1));
        Assert.assertEquals(nonEmptyArray.getBoolean(2), false);
        Assert.assertTrue(nonEmptyArray.hasNull(3));
        Assert.assertFalse(nonEmptyArray.hasNull(2));
        Assert.assertTrue(nonEmptyArray.hasString(4));
        Assert.assertFalse(nonEmptyArray.hasString(3));
        Assert.assertEquals(nonEmptyArray.getString(4), "string");
        Assert.assertTrue(nonEmptyArray.hasArray(5));
        Assert.assertFalse(nonEmptyArray.hasArray(4));
        Assert.assertEquals(nonEmptyArray.getArray(5), EMPTY_ARRAY);

        Json objectInArray = nonEmptyArray.get(0);
        Assert.assertNotNull(objectInArray);
        Assert.assertTrue(objectInArray.isObject());
        Assert.assertTrue(objectInArray.has("contents"));
        Json objectContents = objectInArray.get("contents");
        Assert.assertNotNull(objectContents);
        Assert.assertTrue(objectContents.isNumber());
        Assert.assertEquals(objectContents.getLong(), 1);

        Assert.assertTrue(emptyArray.equals(EMPTY_ARRAY));
        Assert.assertFalse(emptyArray.equals(STRING));
        Assert.assertFalse(emptyArray.equals(DIFFERENT_STRING));
        Assert.assertFalse(emptyArray.equals(ONE));
        Assert.assertFalse(emptyArray.equals(EMPTY_OBJECT));
        Assert.assertFalse(emptyArray.equals(NULL));
        Assert.assertFalse(emptyArray.equals(FALSE));

        emptyArray.add(EMPTY_OBJECT);
        Assert.assertEquals(emptyArray.length(), 1);
        Iterator<Json> iter = emptyArray.iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
        Assert.assertEquals(emptyArray.length(), 0);
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
        Assert.assertFalse(testObject.isDouble("string"));
        Assert.assertTrue(testObject.hasDouble("number"));
        Assert.assertFalse(testObject.hasDouble("nothing"));
        Assert.assertFalse(testObject.hasDouble("string"));
        Assert.assertEquals(testObject.getDouble("number"), 1.0);
        Assert.assertTrue(testObject.getDouble("number") != 2.0);
        Assert.assertEquals(testObject.getDouble("number", 2.0), 1.0);
        Assert.assertEquals(testObject.getDouble("string", 2.0), 2.0);
        Assert.assertEquals(testObject.getDouble("nothing", 2.0), 2.0);
        Assert.assertTrue(EMPTY_ARRAY.equals(testObject.getArray("array", null)));
        Assert.assertFalse(EMPTY_ARRAY.equals(testObject.getArray("not valid key", null)));
        Assert.assertTrue(EMPTY_OBJECT.equals(testObject.getObject("object", null)));
        Assert.assertFalse(EMPTY_OBJECT.equals(testObject.getObject("not valid key", null)));

        Assert.assertTrue(testObject.isLong("number"));
        Assert.assertFalse(testObject.isLong("string"));
        Assert.assertTrue(testObject.hasLong("number"));
        Assert.assertFalse(testObject.hasLong("string"));
        Assert.assertFalse(testObject.hasLong("nothing"));
        Assert.assertEquals(testObject.getLong("number"), 1);
        Assert.assertTrue(testObject.getLong("number") != 2);
        Assert.assertEquals(testObject.getLong("number", 2), 1);
        Assert.assertEquals(testObject.getLong("string", 2), 2);
        Assert.assertEquals(testObject.getLong("nothing", 2), 2);

        Assert.assertTrue(testObject.isString("string"));
        Assert.assertFalse(testObject.isString("number"));
        Assert.assertTrue(testObject.hasString("string"));
        Assert.assertFalse(testObject.hasString("nothing"));
        Assert.assertFalse(testObject.hasString("number"));
        Assert.assertEquals(testObject.getString("string"), "string");
        Assert.assertEquals(testObject.getString("string", "nothing"), "string");
        Assert.assertFalse(testObject.getString("string").equals("blah"));
        Assert.assertEquals(testObject.getString("number", "nothing"), "nothing");
        Assert.assertEquals(testObject.getString("nothing", "nothing"), "nothing");


        Assert.assertTrue(testObject.isBoolean("bool"));
        Assert.assertFalse(testObject.isBoolean("number"));
        Assert.assertFalse(testObject.hasBoolean("number"));
        Assert.assertTrue(testObject.hasBoolean("bool"));
        Assert.assertEquals(testObject.getBoolean("bool"), true);
        Assert.assertEquals(testObject.getBoolean("bool", false), true);
        Assert.assertEquals(testObject.getBoolean("number", false), false);
        Assert.assertEquals(testObject.getBoolean("nothing", false), false);

        Assert.assertTrue(testObject.isNull("null"));
        Assert.assertFalse(testObject.isNull("string"));
        Assert.assertTrue(testObject.hasNull("null"));
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

        Assert.assertTrue(emptyObject.equals(EMPTY_OBJECT));
        Assert.assertFalse(emptyObject.equals(STRING));
        Assert.assertFalse(emptyObject.equals(DIFFERENT_STRING));
        Assert.assertFalse(emptyObject.equals(ONE));
        Assert.assertFalse(emptyObject.equals(EMPTY_ARRAY));
        Assert.assertFalse(emptyObject.equals(NULL));
        Assert.assertFalse(emptyObject.equals(FALSE));

        Json update = Json.fromString("{\"contents\": 2}");
        nonEmptyObject.update(update);
        Assert.assertEquals(nonEmptyObject.getLong("contents"), 2);

        try {
            nonEmptyObject.update(null);
            Assert.fail();
        } catch (Exception e) {}

    }

    public void testToString() throws Throwable {
        String testJsonString = testJson.toString();
        Json testJson2 = Json.fromString(testJsonString);
        Assert.assertEquals(testJson, testJson2);
    }
}
