package com.jeremydeanlakey.json.jval;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jint extends Jval {
    private int value;

    public Jint(int value){
        this.value = value;
    }

    @Override
    public int getInt(){
        return value;
    }
}
