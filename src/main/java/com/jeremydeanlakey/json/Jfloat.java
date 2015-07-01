package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jfloat extends Json {
    private float value;

    protected Jfloat(float value){ this.value = value;}

    @Override
    public boolean isFloat(){return true;}

    @Override
    public float getFloat(){return value;}
}
