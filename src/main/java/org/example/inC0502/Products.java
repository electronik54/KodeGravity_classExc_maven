package org.example.inC0502;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class Products {

    private String name;
    private final int sku;
    private double price;
    private boolean available;
    private int stock = 0;

    public Products(String name, int sku, double price, boolean available, int stock) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.available = available;
        this.stock = stock;
    }

    public void setStock(int i) {
        if (i < 0) throw new IllegalArgumentException("Stock cannot be less than 0");
        stock = i;
    }

    public void setName(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("name cannot be empty");
        this.name = name;
    }

    public void setPrice(int price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be less than 0");
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String toString() {
        return "[PRODUCT::SKU" + getSku() + "|NAME:" + getName() + "]";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (getSku() != ((Products) o).getSku()) return false;

        Products products = (Products) o;
        return sku == products.sku;
    }

    public int hashCode() {
        return Objects.hash(sku);
    }
}