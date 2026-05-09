package org.example.inC0509;

/*
* presentation in GC
* inclass stest
* */

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Products> prods=new ArrayList<>(Arrays.asList(
                new Products("d4", "Phone", "Apple", 999),
                new Products("a1", "banana", "noFrills", 2),
                new Products("c3", "Laptop", "Framework", 2000),
                new Products("b2", "charger", "Apple", 150),
                new Products("e5", "Car", "Honda", 30000)
        ));

        System.out.print("Product with highest value: ");
        Collections.sort(prods);
        System.out.println(prods.get(prods.size() - 1));

        System.out.print("\nSorted list: ");
        Collections.sort(prods);
        System.out.println(prods);

        System.out.print("\nAPPLE PRODUCTS: ");
        List<Products> apple = prods.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase("Apple"))
                .collect(Collectors.toList());
        System.out.println(apple);

        System.out.print("\nLIST OF PRODUCTS WITH PRICE <$500: ");
        List<Products> lessThan500 = prods.stream()
                .filter(p -> p.getPrice()<500)
                .collect(Collectors.toList());
        System.out.println(lessThan500);
    }
}
