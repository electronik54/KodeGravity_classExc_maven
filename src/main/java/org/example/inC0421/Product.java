package org.example.inC0421;

import java.util.Objects;

public class Product {
    String name;
    int id;

    public Product(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product:" + name + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name);
//        return id == Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
//        return Objects.hash(name);
    }

}
