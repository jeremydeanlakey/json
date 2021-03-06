package com.jeremydeanlakey.json;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public abstract class Json implements Iterable<Json> {
    protected static String NOT_OBJECT = "Not an object";
    protected static String NOT_ARRAY = "Not an array";
    protected static String NOT_ARRAY_OBJECT = "Not an array or object";
    protected static String NOT_NUMBER = "Not an number";
    protected static String NOT_BOOLEAN = "Not an boolean value";
    protected static String NOT_STRING = "Not an string value";
    protected static String ITERATOR_ERROR = "Json.iterator() only supported for arrays or objects";

    public boolean isNull() {return false;}
    public boolean isBoolean() {return false;}
    public boolean isNumber() {return false;}
    public boolean isString() {return false;}
    public boolean isArray() {return false;}
    public boolean isObject() {return false;}

    public boolean isEmpty() { throw new RuntimeException(NOT_ARRAY_OBJECT); }

    public boolean getBoolean(){throw new RuntimeException(NOT_BOOLEAN);}
    public long getLong(){throw new RuntimeException(NOT_NUMBER);}
    public double getDouble(){throw new RuntimeException(NOT_NUMBER);}
    public String getString(){throw new RuntimeException(NOT_STRING);}

    public boolean isNull(String key) {throw new RuntimeException(NOT_OBJECT);}
    public boolean isBoolean(String key) {throw new RuntimeException(NOT_OBJECT);}
    public boolean isLong(String key) {throw new RuntimeException(NOT_OBJECT);}
    public boolean isDouble(String key) {throw new RuntimeException(NOT_OBJECT);}
    public boolean isString(String key) {throw new RuntimeException(NOT_OBJECT);}
    public boolean isObject(String key) {throw new RuntimeException(NOT_OBJECT);}
    public boolean isArray(String key) {throw new RuntimeException(NOT_OBJECT);}

    public boolean has(String key){throw new RuntimeException(NOT_OBJECT);}
    public boolean hasNull(String key) {throw new RuntimeException(NOT_OBJECT);}
    public boolean hasBoolean(String key){throw new RuntimeException(NOT_OBJECT);}
    public boolean hasLong(String key){throw new RuntimeException(NOT_OBJECT);}
    public boolean hasDouble(String key){throw new RuntimeException(NOT_OBJECT);}
    public boolean hasString(String key){throw new RuntimeException(NOT_OBJECT);}
    public boolean hasObject(String key){throw new RuntimeException(NOT_OBJECT);}
    public boolean hasArray(String key){throw new RuntimeException(NOT_OBJECT);}

    public Json get(String key){throw new RuntimeException(NOT_OBJECT);}
    public boolean getBoolean(String key){throw new RuntimeException(NOT_OBJECT);}
    public long getLong(String key){throw new RuntimeException(NOT_OBJECT);}
    public double getDouble(String key){throw new RuntimeException(NOT_OBJECT);}
    public String getString(String key){throw new RuntimeException(NOT_OBJECT);}
    public Json getArray(String key){throw new RuntimeException(NOT_OBJECT);}
    public Json getObject(String key){throw new RuntimeException(NOT_OBJECT);}

    public Json get(String key, Json def){throw new RuntimeException(NOT_OBJECT);}
    public boolean getBoolean(String key, boolean def){throw new RuntimeException(NOT_OBJECT);}
    public long getLong(String key, long def){throw new RuntimeException(NOT_OBJECT);}
    public double getDouble(String key, double def){throw new RuntimeException(NOT_OBJECT);}
    public String getString(String key, String def){throw new RuntimeException(NOT_OBJECT);}
    public Json getArray(String key, Jarray def){throw new RuntimeException(NOT_OBJECT);}
    public Json getObject(String key, Jobject def){throw new RuntimeException(NOT_OBJECT);}

    public void update(Json object){throw new RuntimeException(NOT_OBJECT);}

    public boolean hasNull(int index) {throw new RuntimeException(NOT_ARRAY);}
    public boolean hasBoolean(int index){throw new RuntimeException(NOT_ARRAY);}
    public boolean hasLong(int index){throw new RuntimeException(NOT_ARRAY);}
    public boolean hasDouble(int index){throw new RuntimeException(NOT_ARRAY);}
    public boolean hasString(int index){throw new RuntimeException(NOT_ARRAY);}
    public boolean hasArray(int index){throw new RuntimeException(NOT_ARRAY);}
    public boolean hasObject(int index){throw new RuntimeException(NOT_ARRAY);}

    public Json get(int index){throw new RuntimeException(NOT_ARRAY);}
    public boolean getBoolean(int index){throw new RuntimeException(NOT_ARRAY);}
    public long getLong(int index){throw new RuntimeException(NOT_ARRAY);}
    public double getDouble(int index){throw new RuntimeException(NOT_ARRAY);}
    public String getString(int index){throw new RuntimeException(NOT_ARRAY);}
    public Json getArray(int index){throw new RuntimeException(NOT_ARRAY);}
    public Json getObject(int index){throw new RuntimeException(NOT_ARRAY);}


    public Set<String> keys() { throw new RuntimeException(NOT_OBJECT); }
    protected void add(Json value){throw new RuntimeException(NOT_ARRAY);}
    protected void put(String key, Json value){throw new RuntimeException(NOT_OBJECT);}
    public int length() { throw new RuntimeException(NOT_ARRAY); }


    protected Json(){} // Disallow instantiation from outside.

    public static Json newObject() { return new Jobject(); }
    public static Json newArray() { return new Jarray(); }
    public static Json fromString (String string){ return Jtparser.stringToJson(string); }

    @Override public Iterator<Json> iterator() { throw new RuntimeException(ITERATOR_ERROR); }

    protected abstract void writeTo(StringBuilder builder);
    @Override public String toString() {StringBuilder sb = new StringBuilder(); writeTo(sb); return sb.toString(); }

    public static final boolean isNull(Json unknown){ return (unknown instanceof Json) && unknown.isNull(); }
    public static final boolean isBoolean(Json unknown){ return(unknown instanceof Json) && unknown.isBoolean(); }
    public static final boolean isNumber(Json unknown){ return (unknown instanceof Json) && unknown.isNumber(); }
    public static final boolean isString(Json unknown){ return unknown instanceof Jstring; }
    public static final boolean isArray(Json unknown){ return unknown instanceof Jarray; }
    public static final boolean isObject(Json unknown){ return unknown instanceof Jobject; }
}
