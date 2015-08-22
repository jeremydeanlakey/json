package com.jeremydeanlakey.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jarray extends Json {
    private static String REMOVE_ERROR = "Remove is not permitted on JarrayIterator";
    private List<Json> values;

    class JarrayIterator implements Iterator<Json> {
        private int i = 0;
        @Override public boolean hasNext() { return i < values.size(); }
        @Override public Json next() { return values.get(i++); }
        @Override public void remove() { throw new RuntimeException(REMOVE_ERROR); }
    }

    @Override public Iterator<Json> iterator() { return new JarrayIterator(); }

    protected Jarray() { values = new ArrayList<>(); }

    @Override protected void add(Json value) { values.add(value); }
    @Override public int length() { return values.size(); }
    @Override public boolean isArray() { return true; }
    @Override public boolean isEmpty() { return values.isEmpty(); }

    private boolean has(int i) { return i < length();}
    @Override public boolean hasNull(int i) { return has(i) && get(i).isNull(); }
    @Override public boolean hasBoolean(int i){ return has(i) && get(i).isBoolean(); }
    @Override public boolean hasLong(int i){ return has(i) && get(i).isNumber(); }
    @Override public boolean hasDouble(int i){ return has(i) && get(i).isNumber(); }
    @Override public boolean hasString(int i){ return has(i) && get(i).isString(); }
    @Override public boolean hasArray(int i){ return has(i) && get(i).isArray(); }
    @Override public boolean hasObject(int i){ return has(i) && get(i).isObject(); }

    @Override public Json get(int index) { return values.get(index); }
    @Override public boolean getBoolean(int i){ return get(i).getBoolean(); }
    @Override public long getLong(int i){ return get(i).getLong(); }
    @Override public double getDouble(int i){ return get(i).getDouble(); }
    @Override public String getString(int i){ return get(i).getString(); }
    @Override public Json getArray(int i){ return get(i); }
    @Override public Json getObject(int i){ return get(i); }

    @Override
    public void writeTo(StringBuilder builder) {
        builder.append('[');
        boolean first = true;
        for (Json item: values) {
            if (!first)
                builder.append(',');
            first = false;
            item.writeTo(builder);
        }
        builder.append(']');
    }

    private boolean equals(List<Json> values) { return values.equals(this.values); }
    @Override public boolean equals(Object o) { return (o instanceof Jarray) && ((Jarray)o).equals(values); }
}