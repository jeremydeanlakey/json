# json

A library to make it easier for me to deal with json in Android. The style is optimized to my preferences, and not necessarily common best practices.

Here are some key differences in how to use this library vs JSONObject:

1. To avoid try-catch blocks, I throw RuntimeExceptions.  To keep code safe, you must have an if-statement before each dangerous method call.  As a personal preference, I find an if-statements to be more pleasant than try-catch.

For example, this:

```
Double myDouble = null;
try {
    myDouble = myJson.getDouble("key");
    doSomethingWithDouble(myDouble);
}
catch {
    // do something with exception
}
```

becomes this:

```
if (myJson.hasDouble("key")) {
    doSomethingWithDouble(myJson.getDouble("key"));
}
```

2. I use get(key, default) for getOrDefault functions (which are called Opt in JSONObject).  In my opinion, the JSONObject Opt(key, default) is too confusing.  Arguably, "getOrDefault" is more clear than simply "get", but because I've used Python, the "get" makes sense to me and the getOrDefault gets too verbose when, for example, you need to getBooleanOrDefault.

3. I find easier to handle JSON in a dynamically typed language.  So I made a main class (Json) and subclassed it with different types of values (Jnull, Jobject, Jstring, etc.).  This way, you can fetch a value from an object without knowing what type of value it is.  You can then test the object type with functions like isBoolean() and convert it to a regular value with functions like toBoolean().

