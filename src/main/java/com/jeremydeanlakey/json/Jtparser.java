package com.jeremydeanlakey.json;

import android.util.Log;
import android.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jeremydeanlakey on 5/01/16.
 */
public class Jtparser {
    private Jtokenizer tokenizer;

    public Jtparser(String src) { tokenizer = new Jtokenizer(src); }
}
