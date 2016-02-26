package com.jeremydeanlakey.json;

/**
 * Created by jeremydeanlakey on 2/25/16.
 */
abstract class Jtoken {
    protected abstract boolean isJsonValue();
    protected abstract Json getJsonValue();
    protected abstract boolean isObjectStart();
    protected abstract boolean isObjectEnd();
    protected abstract boolean isColon();
    protected abstract boolean isArrayStart();
    protected abstract boolean isArrayEnd();
    protected abstract boolean isComma();
}
