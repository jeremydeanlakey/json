package com.jeremydeanlakey.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jobject extends Json {
    private Map<String, Json> map;
    private static final String QUOTE = "\"";
    protected Jobject(){map = new HashMap();}


    @Override public boolean isEmpty() {return map.isEmpty(); }
    @Override public boolean isObject() { return true;}

    // TODO isArray and isObject
    @Override public boolean isNull(String key) { return map.get(key).isNull(); }
    @Override public boolean isBoolean(String key) { return map.get(key).isBoolean(); }
    @Override public boolean isLong(String key) { return map.get(key).isNumber(); }
    @Override public boolean isDouble(String key) { return map.get(key).isNumber(); }
    @Override public boolean isString(String key) { return map.get(key).isString(); }
    @Override public boolean isObject(String key) { return map.get(key).isObject(); }
    @Override public boolean isArray(String key) { return map.get(key).isArray(); }


    @Override public boolean has(String key){return map.containsKey(key);}
    @Override public boolean hasNull(String key) { return has(key) && isNull(key); }
    @Override public boolean hasBoolean(String key){ return has(key) && isBoolean(key); }
    @Override public boolean hasLong(String key){ return has(key) && isLong(key); }
    @Override public boolean hasDouble(String key){ return has(key) && isDouble(key); }
    @Override public boolean hasString(String key){ return has(key) && isString(key); }
    @Override public boolean hasObject(String key){ return has(key) && isObject(key); }
    @Override public boolean hasArray(String key){ return has(key) && isArray(key); }


    @Override public Json get(String key){return map.get(key);}

    @Override public boolean getBoolean(String key){ return map.get(key).getBoolean(); }
    @Override public long getLong(String key){ return map.get(key).getLong(); }
    @Override public double getDouble(String key){ return map.get(key).getDouble(); }
    @Override public String getString(String key){ return map.get(key).getString(); }

    @Override public boolean getBooleanOrDefault(String key, boolean def){ if(hasBoolean(key)) return getBoolean(key); else return def; }
    @Override public long getLongOrDefault(String key, long def){ if(hasLong(key)) return getLong(key); else return def; }
    @Override public double getDoubleOrDefault(String key, double def){ if(hasDouble(key)) return getDouble(key); else return def; }
    @Override public String getStringOrDefault(String key, String def){ if(hasString(key)) return getString(key); else return def; }


    @Override public Set<String> keys() {
        return map.keySet();
    }
    // TODO delete this? Need to decide whether to go immutable or not.
    @Override public void put(String key, Json value){map.put(key, value);}


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("{");
        for (String key: keys()) {
            output.append(QUOTE);
            output.append(key);
            output.append(QUOTE);
            output.append(":");
            output.append(map.get(key));
            output.append(","); // TODO shouldn't apply to last item
        }
        output.append("}");
        return output.toString();
    }
}
