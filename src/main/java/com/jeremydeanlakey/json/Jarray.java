package com.jeremydeanlakey.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jarray extends Json {
    private List<Json> values;
    private int nextIndex = 0;

    protected Jarray(){values = new ArrayList<>();}

    @Override
    public boolean isArray() { return true; }

    @Override
    public void remove() { throw new RuntimeException("Not an object"); }

    @Override
    public Json get(int index) { return values.get(index); }

    @Override
    protected void add(Json value){ values.add(value); }

    @Override
    public boolean hasNext() { return nextIndex < values.size();}

    @Override
    public Json next() { return values.get(nextIndex++);  }

    @Override
    public boolean isEmpty() { return values.isEmpty(); }

    @Override
    public int length() { return values.size(); }

    public boolean isNull(int index) { return values.get(index).isNull(); }
    public boolean getBoolean(int index){ return values.get(index).getBoolean(); }
    public long getLong(int index){ return values.get(index).getLong(); }
    public double getDouble(int index){ return values.get(index).getDouble(); }
    public String getString(int index){ return values.get(index).getString(); }

}
