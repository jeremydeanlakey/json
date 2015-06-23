package com.jeremydeanlakey.json;

import com.jeremydeanlakey.json.jval.Jboolean;
import com.jeremydeanlakey.json.jval.Jdouble;
import com.jeremydeanlakey.json.jval.Jfloat;
import com.jeremydeanlakey.json.jval.Jint;
import com.jeremydeanlakey.json.jval.Jlong;
import com.jeremydeanlakey.json.jval.Jstring;
import com.jeremydeanlakey.json.jval.Jval;

import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Json implements Iterator<Json> {
    public Type NULL = Type.NULL;
    public Type BOOLEAN = Type.BOOLEAN;
    public Type INTEGER = Type.INTEGER;
    public Type LONG= Type.LONG;
    public Type FLOAT= Type.FLOAT;
    public Type DOUBLE = Type.DOUBLE;
    public Type STRING = Type.STRING;
    public Type ARRAY = Type.ARRAY;
    public Type OBJECT = Type.OBJECT;

    private Map<String, Json> map;
    private Jval value;


    public Json(JSONObject jsonObject){
        type = OBJECT;

        map = new HashMap<>();

        Iterator<String> iter = jsonObject.keys();
        while (iter.hasNext()){
            String key = iter.next();
            try {
                Jval value = new Jboolean(jsonObject.getBoolean(key));
                map.put(key,new Json(BOOLEAN, value));
                continue;
            } catch(Exception e){}
            try {
                Jval value = new Jint(jsonObject.getInt(key));
                map.put(key,new Json(INTEGER, value));
                continue;
            } catch(Exception e){}
            try {
                Jval value = new Jlong(jsonObject.getLong(key));
                map.put(key,new Json(LONG, value));
                continue;
            } catch(Exception e){}
            try {
                Jval value = new Jdouble(jsonObject.getDouble(key));
                map.put(key,new Json(DOUBLE, value));
                continue;
            } catch(Exception e){}
            try {
                Jval value = new Jstring(jsonObject.getString(key));
                map.put(key,new Json(STRING, value));
                continue;
            } catch(Exception e){}
            if (jsonObject.isNull(key)){
                map.put(key, new Json());
                continue;
            }
            try {
                Object object = jsonObject.get(key);
                if (object instanceof JSONObject)
                    map.put(key, new Json((JSONObject) object));
                if (object instanceof JSONArray)
                    map.put(key, new Json((JSONArray) object));
            } catch (Exception e) {}
        }

    }

    private Json(JSONArray jsonArray){

    }

    private Json(){
        type = NULL;
    }

    private Json(Type type, Jval value){
        this.type = type;
        this.value = value;
    }

    public Json get(String key){
        return map.get(key);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Json next() {
        return null;
    }

    @Override
    public void remove() {
        throw new RuntimeException("Mutations of JSON not allowed.");
    }

    public enum Type{
        NULL, BOOLEAN, INTEGER, LONG, FLOAT, DOUBLE, STRING, ARRAY, OBJECT
    }

    private Type type;

    public boolean isNull(){
        return type == NULL;
    }

    public boolean isBoolean(){
        return type == BOOLEAN;
    }

    public boolean isNumber(){
        return (type == INTEGER) || (type == LONG) || (type == FLOAT) || (type == DOUBLE);
    }

    public boolean isString(){
        return type == STRING;
    }

    public boolean isArray(){
        return type == ARRAY;
    }

    public boolean isObject(){
        return type == OBJECT;
    }
}
