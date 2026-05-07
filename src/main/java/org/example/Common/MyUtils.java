package org.example.Common;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class MyUtils {

    public static double randomDouble(double minLimit, double maxLimit){
        return ThreadLocalRandom.current().nextDouble(minLimit, maxLimit);
    }
    public static double randomDouble(double minLimit, double maxLimit, int decimalPlaces){
        return roundInt(ThreadLocalRandom.current().nextDouble(minLimit, maxLimit), decimalPlaces);
    }
    public static int randomInt(int minLimit, int maxLimit){
        return new Random().nextInt(maxLimit-minLimit+1)+minLimit;
    }
    public static double roundInt(double number, int decimalPlaces) {
        return BigDecimal.valueOf(number).setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

// region FunctionalInterface
    @FunctionalInterface
    public interface TriFunction<P1, P2, P3, R> {
        R apply(P1 t, P2 u, P3 v);
    }
// endregion
}
