package org.example.inC0428;

import java.util.Arrays;
import java.util.Scanner;


// IMPLEMENT lambda implementation
public class Main {
    public static void main(String[] args) {

        System.out.println(stringE("CodingHorror"));
        System.out.println(stringE("xax"));
        System.out.println(stringE(""));
        System.out.println(stringE("abcdefghijklmnopqrstuv"));


        Scanner sc = new Scanner(System.in);
        boolean continueLoop = true;
        IUtil checkEven = i -> i % 2 == 0;

        while (continueLoop) {
            System.out.print("enter number for <checkEven()>: ");
            System.out.println("checkEven:" + checkEven.checkEven(sc.nextInt()));

            System.out.println("try again? (1)>YES (<0>)>NO");
            if (sc.nextInt() != 1) continueLoop = false;
        }

    }

    public static String stringE(String str) {

        int len = str.length();
        if(len == 0) return str;
        StringBuilder retSb = new StringBuilder();

        for(int idx = 0; idx < len; idx+=4) {
            retSb.append(str.charAt(idx));
            int end=idx+1;
            if(end < len) retSb.append(str.charAt(end));
        }
        return  retSb.toString();
    }



}
