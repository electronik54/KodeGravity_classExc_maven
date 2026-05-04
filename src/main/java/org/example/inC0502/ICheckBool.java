package org.example.inC0502;

@FunctionalInterface
public interface ICheckBool<R, P> {
    R check(P o);
}