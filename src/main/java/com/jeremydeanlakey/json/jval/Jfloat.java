package com.jeremydeanlakey.json.jval;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jfloat extends Jval {
    private float value;

    public Jfloat(float value){
        this.value = value;
    }

    @Override
    public float getFloat(){
        return value;
    }
}
