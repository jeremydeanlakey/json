package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jdouble extends Json {
    private double value;

    protected Jdouble(double value){this.value = value;}

    @Override
    public boolean isDouble() { return true;}

    @Override
    public double getDouble(){return value;}
}
