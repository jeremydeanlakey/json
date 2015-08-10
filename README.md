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
