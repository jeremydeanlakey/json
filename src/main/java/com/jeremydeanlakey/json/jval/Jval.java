package com.jeremydeanlakey.json.jval;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jval {
    public boolean getBoolean(){
        throw new RuntimeException("Not a boolean value");
    }
    public int getInt(){
        throw new RuntimeException("Not an integer value");
    }
    public long getLong(){
        throw new RuntimeException("Not a long value");
    }
    public float getFloat(){
        throw new RuntimeException("Not a float value");
    }
    public double getDouble(){
        throw new RuntimeException("Not a double value");
    }
    public String getString(){
        throw new RuntimeException("Not a String value");
    }
}
