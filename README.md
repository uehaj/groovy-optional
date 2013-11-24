groovy-optional
===============

Extension module which enhances handilng of Java8's Optional Class in Groovy.

Overview
===============

java.util.Optional is a class which has introduced Java SE8.
The purpose of Optional is to handle failure value in type-safe way.
But operations of Optional tend to be messy.
This extension makes the handling of Option more natural and intutive.

For example:

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

The last one is better, but still boilerplates exists.
Apply this extension, you can write equivalent code in following style:

```
Optional<String> fullName = Optional.of{a,b -> String.join(' ', a, b) }(arg1, arg2))
```

or

```
Optional<String> fullName = lastName + firstName
```

How it works?
==============

When a method call on a ``java.util.Optional`` instance invoked, if the method is not defined ``Optional``, the method call is redirected to contained object of the Optional. In this process, it checks all of arguments and object itself, empty or not. If one of arguements or self object is Optional.empty(), the result of the method call yields Optional.empty().

Above are implemented by define a methodMissing() method throught extension module mechanizm of Grooy.

How to use
==============

Easy way of using this module is through grape's grab annotation.

```
@GrabResolver(name="maven-repo", root="https://raw.github.com/uehaj/maven-repo/gh-pages/snapshot")
@Grab("org.jggug.kobo:groovy-optional:0.1")

def str1 = Optional.of("hello")
def str2 = Optional.of("option")
def str3 = Optional.empty()
println str1+str2
println str1+empty
println str1+str2+empty
```

But in my environment, this code fails with error:

```
class groovyx.java8.optional.extension.OptionalGroovyMethods
Caught: groovy.lang.MissingMethodException: No signature of method: java.lang.String.hello() is applicable for argument types: () values: []
Possible solutions: sleep(long), sleep(long, groovy.lang.Closure), split(), next(), split(), next()
groovy.lang.MissingMethodException: No signature of method: java.lang.String.hello() is applicable for argument types: () values: []
Possible solutions: sleep(long), sleep(long, groovy.lang.Closure), split(), next(), split(), next()
	at groovyx.comprehension.a.run(a.groovy:10)
```

Java se 8 JVM or API has some issue with Groovy module method extension. This issue has reported.(http://jira.codehaus.org/browse/GROOVY-6447). Of cource JDK8 is requred, it means you can't @Grab for this groovy-module. As workaround, donwload  ``groovy-optional-x.x.jar`` from https://github.com/uehaj/maven-repo/tree/gh-pages/snapshot/org/jggug/kobo/groovy-optional/x.x onto to your ~/.groovy/lib folder.


Requirements
==============

This extension works only on Java se8/re environment which supports Optional.flatMap() and tested on build 1.8.0-ea-b115.
Groovy version is 2.0 or higher required and tested only on Groovy 2.2.0.


Note
==============

If the method is defined both of Optional and contained object, call to Optional overrides than contained object.
For example:

```
def a == Optional.of("abc")
a.toString() == "Optional[abc]"  // != "abc"
a.map{it.toString()} == "abc"
```


FAQs
==============

Does it work under ``@TypeChecked``
: No it doesn't now. But it could be possible to write a custom type checker which be aware of delegation of Optional like ``@DelegatesTo`` works. In particular, handle methodMissing event at compile time and check it by target object type.

: How about ``@CompileStatic``?
: No it doesn't. In theory, check and bypassing invokeMethod and dilectry call target object's method, it could be possible. Or it may be smart that to write global AST Transformation which translate method call on Optional type object. But it could be possible only under @TypeChecked/@CompileStatic.

This way of handling Optional is deffer from Java. How about compatibility?
: Upper commpatibility is kept like other Groovy functionality. This extension covers 'NoSuchMethoException' case, so valid Java code never be interpreted in different meaning.

To use this extension, I have to have knowledge of Monad, flatMap, applicative, liftM2, .. ?
: No it isn't.

Implict conversion means unsafe?
: No. Normal value is converted to Optional value, but Optional value is never be converted to normal value. To extract normal value always requires explicit  call to method Optional.get(). Pure value easily be dirty, but dirty value can't be pure inplicitly.

