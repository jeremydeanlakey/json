package com.jeremydeanlakey.json.jval;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jdouble extends Jval {
    private double value;

    public Jdouble(double value){
        this.value = value;
    }

    @Override
    public double getDouble(){
        return value;
    }
}
