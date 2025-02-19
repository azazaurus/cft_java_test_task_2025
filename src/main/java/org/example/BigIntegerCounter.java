package org.example;

import java.math.BigInteger;

public class BigIntegerCounter {
    private int accumulatedIncrement = 0;
    private BigInteger count = BigInteger.ZERO;

    public void count() {
        accumulatedIncrement++;
        if (accumulatedIncrement == Integer.MAX_VALUE) {
            count = count.add(BigInteger.valueOf(Integer.MAX_VALUE));
            accumulatedIncrement = 0;
        }
    }

    public BigInteger getCount() {
        count = count.add(BigInteger.valueOf(accumulatedIncrement));
        accumulatedIncrement = 0;
        return count;
    }
}
