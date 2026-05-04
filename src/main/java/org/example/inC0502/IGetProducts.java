package org.example.inC0502;

@FunctionalInterface
public interface IGetProducts<P, R> {
    R getProducts(P o);
}
