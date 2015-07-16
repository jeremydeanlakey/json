package com.jeremydeanlakey.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jobject extends Json {
    private Map<String, Json> map;

    private static final String QUOTE = "\"";

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
