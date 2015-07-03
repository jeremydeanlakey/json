package com.jeremydeanlakey.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Json implements Iterator<Json> {
    public boolean getBoolean(){throw new RuntimeException("Not a boolean value");}
    public long getLong(){throw new RuntimeException("Not a number");}
    public double getDouble(){throw new RuntimeException("Not a number");}
    public String getString(){throw new RuntimeException("Not a String value");}

    public boolean isNull(String key) {throw new RuntimeException("Not an object");}
    public boolean getBoolean(String key){throw new RuntimeException("Not an object");}
    public long getLong(String key){throw new RuntimeException("Not an object");}
    public double getDouble(String key){throw new RuntimeException("Not an object");}
    public String getString(String key){throw new RuntimeException("Not an object");}

    public boolean isNull(int index) {throw new RuntimeException("Not an array");}
    public boolean getBoolean(int index){throw new RuntimeException("Not an array");}
    public long getLong(int index){throw new RuntimeException("Not an array");}
    public double getDouble(int index){throw new RuntimeException("Not an array");}
    public String getString(int index){throw new RuntimeException("Not an array");}

    public Json get(int index){throw new RuntimeException("Not an array");}
    protected void add(Json value){throw new RuntimeException("Not an array");}
    public Set<String> keys() { throw new RuntimeException("Not an array or object"); }

    public boolean has(String key){throw new RuntimeException("Not an object");}
    public Json get(String key){throw new RuntimeException("Not an object");}
    protected void put(String key, Json value){throw new RuntimeException("Not an object");}

    @Override public boolean hasNext() { throw new RuntimeException("Not an array"); }
    @Override public Json next() { throw new RuntimeException("Not an array"); }
    @Override public void remove() { throw new RuntimeException("Mutation of Json not permitted"); }
    public int length() { throw new RuntimeException("Not an array"); }

    public boolean isEmpty() { throw new RuntimeException("Not an array or object"); }

    public boolean isNull() {return false;}
    public boolean isBoolean() {return false;}
    public boolean isNumber() {return false;}
    public boolean isString() {return false;}
    public boolean isArray() {return false;}
    public boolean isObject() {return false;}



    protected Json(){} // Disallow instantiation from outside.

    public static Json fromString (String string){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(string);

        } catch (JSONException e) {e.printStackTrace();}
        return Json.fromJsonObject(jsonObject);
    }

    public static Json fromJsonObject(JSONObject jsonObject){
        Json output = new Jobject();
        Iterator<String> iter = jsonObject.keys();
        while (iter.hasNext()){
            String key = iter.next();
            if (jsonObject.isNull(key)){
                output.put(key, new Jnull());
                continue;
            }
            if (jsonObject.optJSONObject(key) != null) {
                JSONObject value = jsonObject.optJSONObject(key);
                Json convertedJsonObject = Json.fromJsonObject(value);
                output.put(key, convertedJsonObject);
                continue;
            }
            if (jsonObject.optJSONArray(key) != null) {
                JSONArray value = jsonObject.optJSONArray(key);
                Json convertedJsonObject = Json.fromJsonArray(value);
                output.put(key, convertedJsonObject);
                continue;
            }
            try {
                Json value = new Jboolean(jsonObject.getBoolean(key));
                output.put(key, value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jnumber(jsonObject.getDouble(key));
                output.put(key, value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jstring(jsonObject.getString(key));
                output.put(key, value);
                continue;
            } catch(Exception e){}
        }
        return output;
    }

    public static Json fromJsonArray(JSONArray array){

        Json output = new Jarray();

        for(int i=0; i<array.length(); i++) {
            if (array.isNull(i)) {
                output.add(new Jnull());
                continue;
            }
            if (array.optJSONObject(i) != null) {
                JSONObject value = array.optJSONObject(i);
                Json convertedarray = Json.fromJsonObject(value);
                output.add(convertedarray);
                continue;
            }
            if (array.optJSONArray(i) != null) {
                JSONArray value = array.optJSONArray(i);
                Json convertedarray = Json.fromJsonArray(value);
                output.add(convertedarray);
                continue;
            }
            try {
                Json value = new Jboolean(array.getBoolean(i));
                output.add(value);
                continue;
            } catch (Exception e) {
            }
            try {
                Json value = new Jnumber(array.getDouble(i));
                output.add(value);
                continue;
            } catch (Exception e) {
            }
            try {
                Json value = new Jstring(array.getString(i));
                output.add(value);
                continue;
            } catch (Exception e) {
            }
        }
        return output;
    }
}
