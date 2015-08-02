package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jnumber extends Json {
    private double value;

    protected Jnumber(double value){this.value = value;}

    @Override public boolean isNumber() { return true;}

    @Override public long getLong(){return (new Double(value).longValue());}

    @Override public double getDouble(){return value;}

    @Override public String toString() { return String.valueOf(value); }

    @Override public boolean equals(Object o) { return (o instanceof Jnumber) && (((Jnumber)o).getDouble() == value); }
}
