package com.jeremydeanlakey.json;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
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


    private Json EMPTY_ARRAY, NULL, EMPTY_OBJECT, OBJECT_A1_B2, ONE, STRING, STRING_TRUE, STRING_NULL,
            STRING_ONE_POINT_FIVE, DIFFERENT_STRING, TRUE, FALSE, NON_EMPTY_ARRAY;

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
        OBJECT_A1_B2 = Json.fromString("{\"A\": 1, \"B\": 2}");
        EMPTY_OBJECT = Json.fromJsonObject(jo);
        NULL = new Jnull();
        ONE = new Jnumber(1);
        STRING = new Jstring("I am a string");
        STRING_TRUE = new Jstring("true");
        STRING_NULL = new Jstring("null");
        STRING_ONE_POINT_FIVE = new Jstring("1.5");
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

        try {
            Json update = Json.fromString("{\"contents\": 2}");
            emptyArray.update(null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }

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


        Json testBoolString = Jparser.stringToJson("true");
        Assert.assertTrue(testBoolString.isBoolean());

        Assert.assertEquals(testObject.getString("bool"), "true");
        Assert.assertTrue(testObject.isBoolean("bool"));
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

    }


    public void testHas() throws Throwable {
        Assert.assertTrue(OBJECT_A1_B2.has("A"));
        Assert.assertFalse(OBJECT_A1_B2.has("C"));

        try {
            STRING.has("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testHasNull() throws Throwable {
        // test Jobject.hasNull
        Json testObject = Json.fromString("{\"null\": null, \"notnull\": 1}");
        Assert.assertTrue(testObject.hasNull("null"));
        Assert.assertTrue(testObject.has("notnull"));
        Assert.assertFalse(testObject.hasNull("notnull"));
        Assert.assertFalse(testObject.hasNull("doesn't exist"));
        try {
            testObject.hasNull(0);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }

        // test Jarray.hasNull
        Json testArray = Json.fromString("[null, 1]");
        Assert.assertTrue(testArray.hasNull(0));
        Assert.assertFalse(testArray.hasNull(1));
        try {
            testArray.hasNull("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }


        // test Json.hasNull
        try {
            STRING.hasNull("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            STRING.hasNull(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    public void testHasBoolean() throws Throwable {
        // test Jobject.hasBoolean
        Json testObject = Json.fromString("{\"bool\": true, \"notbool\": 1, \"stringbool\": \"true\"}");
        Assert.assertTrue(testObject.hasBoolean("bool"));
        Assert.assertTrue(testObject.hasBoolean("stringbool"));
        Assert.assertFalse(testObject.hasBoolean("notbool"));
        Assert.assertFalse(testObject.hasBoolean("whatever"));
        try {
            testObject.hasBoolean(0);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }

        // test Jarray.hasBoolean
        Json testArray = Json.fromString("[true, \"false\", 1]");
        Assert.assertTrue(testArray.hasBoolean(0));
        Assert.assertTrue(testArray.hasBoolean(1));
        Assert.assertFalse(testArray.hasBoolean(2));
        try {
            testArray.hasBoolean("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }

        // test Json.hasBoolean
        try {
            STRING.hasBoolean("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            STRING.hasBoolean(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    public void testHasLong() throws Throwable {
        // test Jobject.hasLong
        Json testObject = Json.fromString("{\"long\": 1, \"notlong\": \"a\"}");
        Assert.assertTrue(testObject.hasLong("long"));
        Assert.assertFalse(testObject.hasLong("notlong"));
        Assert.assertFalse(testObject.hasLong("whatever"));
        try {
            testObject.hasLong(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }

        // test Jarray.hasLong
        Json testArray = Json.fromString("[1, \"false\"]");
        Assert.assertTrue(testArray.hasLong(0));
        Assert.assertFalse(testArray.hasLong(1));
        try {
            testArray.hasLong("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }

        // test Json.hasLong
        try {
            STRING.hasLong("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            STRING.hasLong(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    public void testHasDouble() throws Throwable {
        // test Jobject.hasDouble
        Json testObject = Json.fromString("{\"long\": 1, \"notlong\": \"a\"}");
        Assert.assertTrue(testObject.hasDouble("long"));
        Assert.assertFalse(testObject.hasDouble("notlong"));
        Assert.assertFalse(testObject.hasDouble("whatever"));
        try {
            testObject.hasDouble(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }

        // test Jarray.hasDouble
        Json testArray = Json.fromString("[1, \"false\"]");
        Assert.assertTrue(testArray.hasDouble(0));
        Assert.assertFalse(testArray.hasDouble(1));
        try {
            testArray.hasDouble("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }

        // test Json.hasDouble
        try {
            STRING.hasDouble("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            STRING.hasDouble(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    public void testHasString() throws Throwable {
        // test Jobject.hasString
        Json testObject = Json.fromString("{\"long\": 1, \"string\": \"a\"}");
        Assert.assertTrue(testObject.hasString("string"));
        Assert.assertFalse(testObject.hasString("long"));
        Assert.assertFalse(testObject.hasString("whatever"));
        try {
            testObject.hasString(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }

        // test Jarray.hasString
        Json testArray = Json.fromString("[1, \"a\"]");
        Assert.assertTrue(testArray.hasString(1));
        Assert.assertFalse(testArray.hasString(0));
        try {
            testArray.hasString("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }

        // test Json.hasString
        try {
            STRING.hasString("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            STRING.hasString(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    public void testHasArray() throws Throwable {
        // test Jobject.hasArray
        Json testObject = Json.fromString("{\"object\": {}, \"array\": []}");
        Assert.assertTrue(testObject.hasArray("array"));
        Assert.assertFalse(testObject.hasArray("object"));
        Assert.assertFalse(testObject.hasArray("whatever"));
        try {
            testObject.hasArray(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }

        // test Jarray.hasArray
        Json testArray = Json.fromString("[{}, []]");
        Assert.assertTrue(testArray.hasArray(1));
        Assert.assertFalse(testArray.hasArray(0));
        try {
            testArray.hasArray("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }

        // test Json.hasArray
        try {
            STRING.hasArray("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            STRING.hasArray(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    public void testHasObject() throws Throwable {
        // test Jobject.hasObject
        Json testObject = Json.fromString("{\"object\": {}, \"array\": []}");
        Assert.assertFalse(testObject.hasObject("array"));
        Assert.assertTrue(testObject.hasObject("object"));
        Assert.assertFalse(testObject.hasObject("whatever"));
        try {
            testObject.hasObject(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }

        // test Jarray.hasObject
        Json testArray = Json.fromString("[{}, []]");
        Assert.assertTrue(testArray.hasObject(0));
        Assert.assertFalse(testArray.hasObject(1));
        try {
            testArray.hasObject("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }

        // test Json.hasObject
        try {
            STRING.hasObject("anything");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            STRING.hasObject(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    // TODO: test get or default functions
    public void testGet() throws Throwable {
        Json testObject = Json.fromString("{\"object\": {}, \"array\": []}");
        Assert.assertNotNull(testObject.get("object"));
        Assert.assertTrue(testObject.get("object").isObject());
        Assert.assertFalse(testObject.has("whatever"));
        Assert.assertNull(testObject.get("whatever"));
        Assert.assertTrue(testObject.get("whatever", STRING).isString());
        Assert.assertFalse(testObject.get("object", STRING).isString());
        try {
            testObject.get(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }


        Json testArray = Json.fromString("[{}, []]");
        Assert.assertNotNull(testArray.get(0));
        Assert.assertTrue(testObject.get(0).isObject());
        try {
            testObject.get(2);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), IndexOutOfBoundsException.class);
        }
        try {
            testObject.get("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }


        try {
            STRING.get(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            STRING.get("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testGetBoolean() throws Throwable {
        Json testObject = Json.fromString("{\"boolean\": true, \"array\": []}");
        Assert.assertNotNull(testObject.getBoolean("boolean"));
        Assert.assertTrue(testObject.getBoolean("boolean", false));
        Assert.assertFalse(testObject.getBoolean("whatever", false));
        try {
            testObject.getBoolean("array");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_BOOLEAN);
        }
        try {
            testObject.getBoolean("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        Json testArray = Json.fromString("[true, []]");
        Assert.assertNotNull(testArray.getBoolean(0));
        try {
            testObject.getBoolean(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_BOOLEAN);
        }
        try {
            testObject.getBoolean(2);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), IndexOutOfBoundsException.class);
        }
        try {
            testObject.getBoolean("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        try {
            STRING.getBoolean(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            STRING.getBoolean("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testGetLong() throws Throwable {
        Json testObject = Json.fromString("{\"number\": 1, \"array\": []}");
        Assert.assertNotNull(testObject.getLong("number"));
        Assert.assertEquals(1, testObject.getLong("number", 2));
        Assert.assertEquals(2, testObject.getLong("whatever", 2));
        try {
            testObject.getLong("array");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_NUMBER);
        }
        try {
            testObject.getLong("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        Json testArray = Json.fromString("[1, []]");
        Assert.assertNotNull(testArray.getLong(0));
        try {
            testObject.getLong(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_NUMBER);
        }
        try {
            testObject.getLong(2);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), IndexOutOfBoundsException.class);
        }
        try {
            testObject.getLong("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        try {
            STRING.getLong(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            STRING.getLong("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testGetDouble() throws Throwable {
        Json testObject = Json.fromString("{\"number\": 1, \"array\": []}");
        Assert.assertNotNull(testObject.getDouble("number"));
        Assert.assertEquals(1, testObject.getDouble("number", 2));
        Assert.assertEquals(2, testObject.getDouble("whatever", 2));
        try {
            testObject.getDouble("array");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_NUMBER);
        }
        try {
            testObject.getDouble("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        Json testArray = Json.fromString("[1, []]");
        Assert.assertNotNull(testArray.getDouble(0));
        try {
            testObject.getDouble(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_NUMBER);
        }
        try {
            testObject.getDouble(2);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), IndexOutOfBoundsException.class);
        }
        try {
            testObject.getDouble("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        try {
            STRING.getDouble(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            STRING.getDouble("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testGetString() throws Throwable {
        Json testObject = Json.fromString("{\"string\": \"memimo\", \"array\": []}");
        Assert.assertNotNull(testObject.getString("string"));
        Assert.assertEquals("memimo", testObject.getString("string", "bzzzt"));
        Assert.assertEquals("bzzzt", testObject.getString("whatever", "bzzzt"));
        try {
            testObject.getString("array");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_STRING);
        }
        try {
            testObject.getString("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        Json testArray = Json.fromString("[\"memimo\", []]");
        Assert.assertNotNull(testArray.getString(0));
        try {
            testObject.getString(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_STRING);
        }
        try {
            testObject.getString(2);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), IndexOutOfBoundsException.class);
        }
        try {
            testObject.getString("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        try {
            STRING.getString(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            STRING.getString("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testGetArray() throws Throwable {
        Json testObject = Json.fromString("{\"string\": \"memimo\", \"array\": []}");
        Assert.assertNotNull(testObject.getArray("array"));
        Assert.assertEquals(EMPTY_ARRAY, testObject.getArray("array", NON_EMPTY_ARRAY));
        Assert.assertNotEquals(EMPTY_ARRAY, testObject.getArray("whatever", NON_EMPTY_ARRAY));
        try {
            testObject.getArray("array");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            testObject.getArray("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        Json testArray = Json.fromString("[\"memimo\", []]");
        Assert.assertNotNull(testArray.getArray(1));
        try {
            testObject.getArray(0);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            testObject.getArray(2);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), IndexOutOfBoundsException.class);
        }
        try {
            testObject.getArray("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        try {
            STRING.getArray(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            STRING.getArray("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testGetObject() throws Throwable {
        Json testObject = Json.fromString("{\"string\": \"memimo\", \"object\": {}}");
        Assert.assertNotNull(testObject.getObject("object"));
        try {
            testObject.getObject("string");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            testObject.getArray("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        Json testArray = Json.fromString("[\"memimo\", {}]");
        Assert.assertNotNull(testArray.getObject(1));
        try {
            testObject.getObject(0);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
        try {
            testObject.getObject(2);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), IndexOutOfBoundsException.class);
        }
        try {
            testObject.getObject("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), NullPointerException.class);
        }


        try {
            STRING.getObject(1);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
        try {
            STRING.getObject("whatever");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testIsEmpty() throws Throwable {
        Assert.assertTrue(EMPTY_ARRAY.isEmpty());
        Assert.assertTrue(EMPTY_OBJECT.isEmpty());
        Assert.assertFalse(NON_EMPTY_ARRAY.isEmpty());
        Assert.assertFalse(OBJECT_A1_B2.isEmpty());

        try {
            STRING.isEmpty();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY_OBJECT);
        }
    }

    public void testAdd() throws Throwable {
        Json array = Json.fromString("[]");
        Assert.assertTrue(array.isEmpty());

        // test add item
        array.add(Json.fromString("1"));
        Assert.assertFalse(array.isEmpty());
        Assert.assertTrue(array.hasLong(0));
        Assert.assertEquals(array.getLong(0), 1);

        // test add null
        array.add(null);
        Assert.assertNull(array.get(1));

        // test add non-array
        try {
            EMPTY_OBJECT.add(Json.fromString("1"));
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    public void testPut() throws Throwable {
        Json object = Json.fromString("{}");
        Assert.assertTrue(object.isEmpty());

        // test update new key-value
        object.put("A", Json.fromString("1"));
        Assert.assertFalse(object.isEmpty());
        Assert.assertTrue(object.hasLong("A"));
        Assert.assertEquals(object.getLong("A"), 1);

        // test update existing key-value
        object.put("A", Json.fromString("2"));
        Assert.assertEquals(object.getLong("A"), 2);

        // test update null
        object.put("null", null);
        Assert.assertNull(object.get("null"));

        // test update non-object
        try {
            EMPTY_ARRAY.put("A", Json.fromString("1"));
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }


    public void testIterator() throws Throwable {

        // TODO should Jobject have an iterator?
        // Assert.assertFalse(EMPTY_OBJECT.iterator().hasNext());
        Assert.assertFalse(EMPTY_ARRAY.iterator().hasNext());

        int arraySize = 0;
        for (Json x: NON_EMPTY_ARRAY)
            arraySize++;
        Assert.assertEquals(NON_EMPTY_ARRAY.length(), arraySize);

        /*
        Json sizeTwo = Json.fromString("{\"A\": 1, \"B\": 2}");
        int objectSize = 0;
        for (Json x: sizeTwo)
            objectSize++;
        Assert.assertEquals(sizeTwo.length(), objectSize);
        */

        try {
            STRING.iterator();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.ITERATOR_ERROR);
        }
    }

    public void testKeys() throws Throwable {

        Assert.assertEquals(EMPTY_OBJECT.keys().size(), 0);

        Json sizeTwo = Json.fromString("{\"A\": 1, \"B\": 2}");
        Assert.assertEquals(sizeTwo.keys().size(), 2);
        Assert.assertTrue(sizeTwo.keys().contains("A"));
        Assert.assertTrue(sizeTwo.keys().contains("B"));
        Assert.assertFalse(sizeTwo.keys().contains("C"));

        try {
            NON_EMPTY_ARRAY.keys();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }

        try {
            STRING.keys();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_OBJECT);
        }
    }

    public void testUpdate() throws Throwable {
        Json nonEmptyObject = testJson.get("nonEmptyObject");

        Json update = Json.fromString("{\"contents\": 2}");
        nonEmptyObject.update(update);
        Assert.assertEquals(nonEmptyObject.getLong("contents"), 2);

        try {
            nonEmptyObject.update(null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Jobject.UPDATE_ERROR);
        }

        try {
            nonEmptyObject.update(Json.fromString("[]"));
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Jobject.UPDATE_ERROR);
        }
    }

    public void testLength() throws Throwable {
        Assert.assertEquals(EMPTY_ARRAY.length(), 0);
        Json array4 = Json.fromString("[1, 2, \"3\", 4]");
        Assert.assertEquals(array4.length(), 4);

        Json obj= Json.fromString("{\"a\":1, \"b\":2}");
        Assert.assertEquals(obj.length(), 2);

        try {
            STRING.length();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), Json.NOT_ARRAY);
        }
    }

    public void testIsNull() throws Throwable {
        Assert.assertFalse(Json.isNull(EMPTY_ARRAY));
        Assert.assertFalse(Json.isNull(EMPTY_OBJECT));
        Assert.assertTrue(Json.isNull(NULL));
        Assert.assertFalse(Json.isNull(ONE));
        Assert.assertFalse(Json.isNull(STRING));
        Assert.assertTrue(Json.isNull(STRING_NULL));
        Assert.assertFalse(Json.isNull(TRUE));

        Assert.assertFalse(EMPTY_ARRAY.isNull());
        Assert.assertFalse(EMPTY_OBJECT.isNull());
        Assert.assertTrue(NULL.isNull());
        Assert.assertFalse(ONE.isNull());
        Assert.assertFalse(STRING.isNull());
        Assert.assertTrue(STRING_NULL.isNull());
        Assert.assertFalse(TRUE.isNull());
    }

    public void testIsBoolean() throws Throwable {
        Assert.assertFalse(Json.isBoolean(EMPTY_ARRAY));
        Assert.assertFalse(Json.isBoolean(EMPTY_OBJECT));
        Assert.assertFalse(Json.isBoolean(NULL));
        Assert.assertFalse(Json.isBoolean(ONE));
        Assert.assertFalse(Json.isBoolean(STRING));
        Assert.assertTrue(Json.isBoolean(TRUE));
        Assert.assertTrue(Json.isBoolean(STRING_TRUE));

        Assert.assertFalse(EMPTY_ARRAY.isBoolean());
        Assert.assertFalse(EMPTY_OBJECT.isBoolean());
        Assert.assertFalse(NULL.isBoolean());
        Assert.assertFalse(ONE.isBoolean());
        Assert.assertFalse(STRING.isBoolean());
        Assert.assertTrue(TRUE.isBoolean());
        Assert.assertTrue(STRING_TRUE.isBoolean());
    }

    public void testIsNumber() throws Throwable {
        // TODO add testIsLong and testIsDouble ?
        Assert.assertFalse(Json.isNumber(EMPTY_ARRAY));
        Assert.assertFalse(Json.isNumber(EMPTY_OBJECT));
        Assert.assertFalse(Json.isNumber(NULL));
        Assert.assertTrue(Json.isNumber(ONE));
        Assert.assertFalse(Json.isNumber(STRING));
        Assert.assertTrue(Json.isNumber(STRING_ONE_POINT_FIVE));
        Assert.assertFalse(Json.isNumber(TRUE));

        Assert.assertFalse(EMPTY_ARRAY.isNumber());
        Assert.assertFalse(EMPTY_OBJECT.isNumber());
        Assert.assertFalse(NULL.isNumber());
        Assert.assertTrue(ONE.isNumber());
        Assert.assertFalse(STRING.isNumber());
        Assert.assertTrue(STRING_ONE_POINT_FIVE.isNumber());
        Assert.assertFalse(TRUE.isNumber());
    }

    public void testIsString() throws Throwable {
        Assert.assertFalse(Json.isString(EMPTY_ARRAY));
        Assert.assertFalse(Json.isString(EMPTY_OBJECT));
        Assert.assertFalse(Json.isString(NULL));
        Assert.assertFalse(Json.isString(ONE));
        Assert.assertTrue(Json.isString(STRING));
        Assert.assertFalse(Json.isString(TRUE));

        Assert.assertFalse(EMPTY_ARRAY.isString());
        Assert.assertFalse(EMPTY_OBJECT.isString());
        Assert.assertFalse(NULL.isString());
        Assert.assertFalse(ONE.isString());
        Assert.assertTrue(STRING.isString());
        Assert.assertFalse(TRUE.isString());
    }

    public void testIsArray() throws Throwable {
        Assert.assertTrue(Json.isArray(EMPTY_ARRAY));
        Assert.assertFalse(Json.isArray(EMPTY_OBJECT));
        Assert.assertFalse(Json.isArray(NULL));
        Assert.assertFalse(Json.isArray(ONE));
        Assert.assertFalse(Json.isArray(STRING));
        Assert.assertFalse(Json.isArray(TRUE));

        Assert.assertTrue(EMPTY_ARRAY.isArray());
        Assert.assertFalse(EMPTY_OBJECT.isArray());
        Assert.assertFalse(NULL.isArray());
        Assert.assertFalse(ONE.isArray());
        Assert.assertFalse(STRING.isArray());
        Assert.assertFalse(TRUE.isArray());
    }

    public void testIsObject() throws Throwable {
        Assert.assertFalse(Json.isObject(EMPTY_ARRAY));
        Assert.assertTrue(Json.isObject(EMPTY_OBJECT));
        Assert.assertFalse(Json.isObject(NULL));
        Assert.assertFalse(Json.isObject(ONE));
        Assert.assertFalse(Json.isObject(STRING));
        Assert.assertFalse(Json.isObject(TRUE));

        Assert.assertFalse(EMPTY_ARRAY.isObject());
        Assert.assertTrue(EMPTY_OBJECT.isObject());
        Assert.assertFalse(NULL.isObject());
        Assert.assertFalse(ONE.isObject());
        Assert.assertFalse(STRING.isObject());
        Assert.assertFalse(TRUE.isObject());
    }

    public void testToString() throws Throwable {
        String testJsonString = testJson.toString();
        Json testJson2 = Json.fromString(testJsonString);
        Assert.assertEquals(testJson, testJson2);
    }

    public void testNewArray() throws Throwable {
        Json array = Json.newArray();
        Assert.assertTrue(array.isArray());
        Assert.assertTrue(array.isEmpty());
        array.add(Json.fromString("1"));
        Assert.assertFalse(array.isEmpty());
    }

    public void testNewObject() throws Throwable {
        Json obj = Json.newObject();
        Assert.assertTrue(obj.isObject());
        Assert.assertTrue(obj.isEmpty());
        obj.put("1", Json.fromString("1"));
        Assert.assertFalse(obj.isEmpty());
    }
}
