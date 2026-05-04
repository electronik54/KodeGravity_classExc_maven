package org.example.inC0421;

import lombok.NonNull;

import java.util.*;

public class Main {

    /*
     * HOMEWORK: Hashcode and equals contract
     * */

    public static void main(String[] args) {

        String str1 = "Not nikhil";
        String str2 = "nikhil";
        String newStr = new String("nikhil");
        char[] chars = {'n', 'i', 'k', 'h', 'i', 'l'};

        System.out.println("cuF: " + cuF("ozymandias"));
        System.out.println("cuF: " + cuF("bzoo"));
        System.out.println("cuF: " + cuF("oxx"));
        System.out.println("cuF: " + cuF("1"));

        System.out.println("HASHCODE (str1):" + str1.hashCode());
        System.out.println("HASHCODE (str2):" + str2.hashCode());
        System.out.println("HASHCODE (newStr):" + newStr.hashCode());
        System.out.println("HASHCODE (new String(chars)):" + new String(chars).hashCode());

        System.out.println("==============================");
        System.out.println();
        Product product = new Product("phone", 122);
        Product product2 = new Product("case", 34253);
        Product product3 = new Product("case", 34253);

        System.out.println("HASHCODE (product):" + product.hashCode());
        System.out.println("HASHCODE (product2):" + product2.hashCode());
        System.out.println("HASHCODE (product3):" + product3.hashCode()); // this entry will generate the same hashcode, unless hashCode() and equals() logics are overridden in the implementing class.

        System.out.println("==============================");
        System.out.println();
        Set<Product> prodSet = new HashSet<>(Arrays.asList(
                new Product("phone", 122),
                new Product("case", 34253),
                new Product("case", 34253121),
                new Product("case", 34253) // this entry will be allowed by default, hashCode() generates same hashcode value.
        ));
        System.out.println("(prodSet):" + prodSet);

        Iterator<Product> prodItr = prodSet.iterator();
        while (prodItr.hasNext()) {
            Product next = prodItr.next();
            System.out.println("HASHCODE (" + next + "):" + next.hashCode());
        }
    }

    public static boolean cuF(String str) {

        int eCount = 0;
        for (char c : str.toLowerCase().toCharArray()) {
            if (c == 'e') eCount++;
        }
        return (0 < eCount && 3 <= eCount);

    }

}