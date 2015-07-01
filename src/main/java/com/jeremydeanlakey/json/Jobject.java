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
}
