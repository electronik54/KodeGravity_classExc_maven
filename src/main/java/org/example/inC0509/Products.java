package org.example.inC0509;

import lombok.Getter;

public class Products implements Comparable<Products> {

    private String id;
    private String name;
    @Getter
    private String brand;
    @Getter
    private double price;

    public Products(String id, String name, String brand, double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    @Override
    public int compareTo(Products o) {
        return price < o.price ? -1 : (price == o.price ? 0 : 1);
    }

    public String toString(){
        return "-Products["+this.id + " " + this.name + " " + this.brand + " " + this.price+"]-";
    }

}
