package com.jeremydeanlakey.json.jval;

/**
 * Created by jeremydeanlakey on 6/22/15.
 */
public class Jstring extends Jval {
    private String value;

    public Jstring(String value){
        this.value = value;
    }

    @Override
    public String getString(){
        return value;
    }
}
