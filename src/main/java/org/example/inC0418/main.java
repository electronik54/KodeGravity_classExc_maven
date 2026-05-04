package org.example.inC0418;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {
        
        List<Car> carLst = new ArrayList<>(Arrays.asList(
                new Car("XJG","Blue","Sedan",45000,"PYK","xN1K"),
                new Car("XJG","red","Sedan",35000,"PYK","xPD"),
                new Car("Tesla","white","SUV",75000,"211","roadster"),
                new Car("Honda","black","Coupe",55000,"sliver-Line","1000 skyliner")
        ));
    }
}