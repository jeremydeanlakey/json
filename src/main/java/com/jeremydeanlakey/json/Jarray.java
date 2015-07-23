package com.jeremydeanlakey.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jarray extends Json {
    private List<Json> values;
    private int nextIndex = 0;

    class JarrayIterator implements Iterator<Json> {
        private int i = 0;

        @Override public boolean hasNext() { return i < values.size(); }

        @Override public Json next() { return values.get(i++); }

        @Override public void remove() { throw new RuntimeException("Remove is not permitted as Json is intended to be immutable."); }
    }


    protected Jarray(){values = new ArrayList<>();}

    @Override public boolean isArray() { return true; }

    @Override public Json get(int index) { return values.get(index); }

    @Override protected void add(Json value){ values.add(value); }

    @Override public Iterator<Json> iterator() { return new JarrayIterator(); }

    @Override public boolean isEmpty() { return values.isEmpty(); }

    @Override public int length() { return values.size(); }

    @Override public boolean hasNull(int index) { return values.get(index).isNull(); }
    @Override public boolean hasBoolean(int index){ return values.get(index).isBoolean(); }
    @Override public boolean hasLong(int index){ return values.get(index).isNumber(); }
    @Override public boolean hasDouble(int index){ return values.get(index).isNumber(); }
    @Override public boolean hasString(int index){ return values.get(index).isString(); }

    @Override public boolean getBoolean(int index){ return values.get(index).getBoolean(); }
    @Override public long getLong(int index){ return values.get(index).getLong(); }
    @Override public double getDouble(int index){ return values.get(index).getDouble(); }
    @Override public String getString(int index){ return values.get(index).getString(); }

    // TODO make more efficient
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("[");
        for (Json item: values) {
            output.append(item.toString());
            output.append(","); // TODO should only apply to the last
        }
        output.append("]");
        return output.toString();
    }
}
