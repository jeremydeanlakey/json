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

    @Override public boolean getBoolean(String key, boolean def){ return hasBoolean(key) ? getBoolean(key) : def; }
    @Override public long getLong(String key, long def){ return hasLong(key) ? getLong(key) : def; }
    @Override public double getDouble(String key, double def){ return hasDouble(key) ? getDouble(key) : def; }
    @Override public String getString(String key, String def){ return hasString(key) ? getString(key) : def; }
    @Override public Json getArray(String key, Jarray def){ return hasArray(key) ? get(key) : def; }
    @Override public Json getObject(String key, Jobject def){ return hasObject(key) ? get(key) : def; }


    @Override public Set<String> keys() { return map.keySet(); }
    @Override public void put(String key, Json value){map.put(key, value);}

    @Override public void update(Json object) {
        if (object == null || !Json.isObject(object))
            throw new RuntimeException("update(Json) requires an object as argument");
        for (String key: object.keys())
            put(key, object.get(key));
    }

    @Override
    public void writeTo(StringBuilder builder) {
        builder.append('{');
        boolean first = true;
        for (String key: keys()) {
            if (!first)
                builder.append(',');
            first = false;
            builder.append(QUOTE);
            builder.append(key);
            builder.append(QUOTE);
            builder.append(':');
            map.get(key).writeTo(builder);
        }
        builder.append('}');
    }

    private boolean equals(Map<String, Json> values) { return values.equals(map); }
    @Override public boolean equals(Object o) { return (o instanceof Jobject) && ((Jobject)o).equals(map); }
}
