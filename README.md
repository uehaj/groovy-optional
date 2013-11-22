groovy-optional
===============

Extension module which enhances handilng of Java8's Optional Class in Groovy.

The Purpose
================

java.util.Option class introduced Java se8, which can handle failure value in type-safe way.

But operations for containd value of Optional tend to be messy. For example:

```
Optional<String> fullName = (lastName.isPresent() && firstName.isPresent()) ? 
        Optional.of(String.join(" ", lastName.get(), firstName.get())) : 
        Optional.empty();
```

```
Optional<String> fullName = 
  lastName.flatMap(ln -> 
  firstName.flatMap(fn -> 
    Optional.of(String.join(" ", ln, fn))));
```

```
Optional<String> fullName = 
  lastName.flatMap(ln -> 
  firstName.map(fn -> 
    String.join(" ", ln, fn)));
```

Last one is better, but still boilerplate exists.

Apply this extension, you can write equivalent code in following style:

```
Optional<String> fullName = Optional.of{ln, fn -> String.join(" ", ln, fn)))(lastName, fullName)}
```

or

```
Optional<String> fullName = lastName + fullName
```
