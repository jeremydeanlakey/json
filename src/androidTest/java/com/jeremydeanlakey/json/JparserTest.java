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
//        Json emptyObject = parser.getJobject();
    }

    public void testGetJarray() throws Throwable {
        // TODO
    }

    public void testGetJobject() throws Throwable {
        // TODO
        Jparser parser = new Jparser("{}");
//        Json emptyObject = parser.getJobject();
    }

    public void testGetJstring() throws Throwable {
        // TODO
        Jparser parser = new Jparser("\"test\"");
        Json string = parser.getJstring();
//         Assert.assertEquals(string.toString(), "test"); // expected:<["test"]> but was:<[test]>
    }

    public void testGetUnknownAlphanumeric() throws Throwable {
        // TODO null
        // TODO string
        Jparser parser = new Jparser("test ");
        Json string = parser.getUnknownAlphanumeric();
//        Assert.assertEquals(string.getString(), "test"); // Unexpected character: 't'.  Expected: '" or '' at 0.
        // TODO boolean
        parser = new Jparser("true ");
        Json booleanTrue = parser.getUnknownAlphanumeric();
        Assert.assertEquals(booleanTrue.getBoolean(), true); // Unexpected character: 't'.  Expected: '" or '' at 0.
        // TODO string
    }

    public void testStringToJson() throws Throwable {
        // Json test = Jparser.stringToJson(TEST_CASE_STRING);
    }
}
