package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jlong extends Json {
    private long value;

    protected Jlong(long value){this.value = value;}

    @Override
    public boolean isLong(){return true;}

    @Override
    public long getLong(){return value;}
}
