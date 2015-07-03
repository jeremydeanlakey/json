package com.jeremydeanlakey.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jobject extends Json {
    private Map<String, Json> map;

    protected Jobject(){map = new HashMap();}

    @Override
    public boolean isObject() { return true;}

    @Override
    public boolean has(String key){return map.containsKey(key);}

    @Override
    public Json get(String key){return map.get(key);}

    @Override
    public void put(String key, Json value){map.put(key, value);}

    @Override
    public Set<String> keys() {
        return map.keySet();
    }

    @Override
    public boolean isEmpty() {return map.isEmpty(); }

    public boolean isNull(String key) { return map.get(key).isNull(); }
    public boolean getBoolean(String key){ return map.get(key).getBoolean(); }
    public long getLong(String key){ return map.get(key).getLong(); }
    public double getDouble(String key){ return map.get(key).getDouble(); }
    public String getString(String key){ return map.get(key).getString(); }
}
