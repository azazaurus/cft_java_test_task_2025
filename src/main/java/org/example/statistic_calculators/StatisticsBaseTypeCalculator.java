package org.example.statistic_calculators;

import org.example.BigIntegerCounter;

import java.math.BigInteger;

public abstract class StatisticsBaseTypeCalculator {
    private final BigIntegerCounter filteredElementsCounter = new BigIntegerCounter();

    public void countFilteredElements() {
        filteredElementsCounter.count();
    }

    public abstract void calculate(String line);

    public abstract void finalizeCalculation();

    public BigInteger getFilteredElementsCount() {
        return filteredElementsCounter.getCount();
    }
}
