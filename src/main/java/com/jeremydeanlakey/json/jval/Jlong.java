package com.jeremydeanlakey.json.jval;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jlong extends Jval {
    private long value;

    public Jlong(long value){
        this.value = value;
    }

    @Override
    public long getLong(){
        return value;
    }
}
