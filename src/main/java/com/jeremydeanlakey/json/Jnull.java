package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 6/23/15.
 */
public class Jnull extends Json {

    protected Jnull(){}

    @Override public boolean isNull() { return true;}

    @Override public String toString() { return "null"; }

    @Override public boolean equals(Object o) { return (o instanceof Jnull); }
}
