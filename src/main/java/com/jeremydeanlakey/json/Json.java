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
    public int getInt(){throw new RuntimeException("Not an integer value");}
    public long getLong(){throw new RuntimeException("Not a long value");}
    public float getFloat(){throw new RuntimeException("Not a float value");}
    public double getDouble(){throw new RuntimeException("Not a double value");}
    public String getString(){throw new RuntimeException("Not a String value");}

    public Json get(int index){throw new RuntimeException("Not an array");}
    protected void add(Json value){throw new RuntimeException("Not an array");}
    public Set<String> keys() { throw new RuntimeException("Not an array or object"); }

    public boolean has(String key){throw new RuntimeException("Not an object");}
    public Json get(String key){throw new RuntimeException("Not an object");}
    protected void put(String key, Json value){throw new RuntimeException("Not an object");}

    @Override public boolean hasNext() { throw new RuntimeException("Not an array"); }
    @Override public Json next() { throw new RuntimeException("Not an array"); }
    @Override public void remove() { throw new RuntimeException("Mutation of Json not permitted"); }

    public boolean isEmpty() { throw new RuntimeException("Not an array or object"); }

    public boolean isNull() {return false;}
    public boolean isBoolean() {return false;}
    public boolean isInt() {return false;}
    public boolean isLong() {return false;}
    public boolean isFloat() {return false;}
    public boolean isDouble() {return false;}
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
            try {
                Json value = new Jboolean(jsonObject.getBoolean(key));
                output.put(key, value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jint(jsonObject.getInt(key));
                output.put(key, value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jlong(jsonObject.getLong(key));
                output.put(key, value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jdouble(jsonObject.getDouble(key));
                output.put(key, value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jstring(jsonObject.getString(key));
                output.put(key, value);
                continue;
            } catch(Exception e){}
            if (jsonObject.isNull(key)){
                output.put(key, new Jnull());
                continue;
            }
            try {
                Object object = jsonObject.get(key);
                if (object instanceof JSONObject)
                    output.put(key, Json.fromJsonObject((JSONObject) object));
                if (object instanceof JSONArray) {
                    output.put(key, Json.fromJsonArray((JSONArray) object));
                }
            } catch (Exception e) {}
        }

        return output;
    }

    public static Json fromJsonArray(JSONArray array){
        Json output = new Jarray();

        for(int i=0; i<array.length(); i++) {
            try {
                Json value = new Jboolean(array.getBoolean(i));
                output.add(value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jint(array.getInt(i));
                output.add(value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jlong(array.getLong(i));
                output.add(value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jdouble(array.getDouble(i));
                output.add(value);
                continue;
            } catch(Exception e){}
            try {
                Json value = new Jstring(array.getString(i));
                output.add(value);
                continue;
            } catch(Exception e){}
            if (array.isNull(i)){
                output.add(new Jnull());
                continue;
            }
            try {
                Object object = array.get(i);
                if (object instanceof JSONObject)
                    output.add(Json.fromJsonObject((JSONObject) object));
                else if (object instanceof JSONArray) {
                    output.add(Json.fromJsonArray((JSONArray) object));
                }
                else {
                    throw new RuntimeException("Can't convert to Json: " + object.toString());
                }
            } catch (Exception e) {}
        }
        return output;
    }
}
