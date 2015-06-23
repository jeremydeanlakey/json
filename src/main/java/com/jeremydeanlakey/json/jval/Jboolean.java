package com.jeremydeanlakey.json.jval;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jboolean extends Jval {
    private boolean value;

    public Jboolean(boolean value){
        this.value = value;
    }

    @Override
    public boolean getBoolean(){
        return value;
    }
}
