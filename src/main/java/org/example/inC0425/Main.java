package org.example.inC0425;

public class Main {
    public static void main(String[] args) {

        TestInterface testInterface = (str) -> {
            str = str.trim();
            return str.toUpperCase();
        };

        System.out.println(testInterface.printTextInLowerCase("someString"));

        System.out.println(testInterface.printTextInUpperCase("someOtherString"));


    }
}
