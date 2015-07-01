package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jint extends Json {
    private int value;

    protected Jint(int value){this.value = value;}

    @Override
    public boolean isInt(){return true;}

    @Override
    public int getInt(){return value;}
}
