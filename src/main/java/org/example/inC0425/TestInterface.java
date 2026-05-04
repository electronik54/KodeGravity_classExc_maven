package org.example.inC0425;

//A lambda in Java is a short way to write a method — without giving it a name, without writing a class, and without boilerplate.

@FunctionalInterface
public interface TestInterface {

    String printTextInUpperCase(String s);
    //String printTextInUpperCase2(String s);

    default String printTextInLowerCase(String s) {
        return s.toLowerCase();
    }

}
