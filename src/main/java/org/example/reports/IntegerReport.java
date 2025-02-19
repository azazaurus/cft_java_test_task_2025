package org.example.reports;

import java.math.BigInteger;

public class IntegerReport {
    public final BigInteger filteredElementsCount;
    public final BigInteger maxValue;
    public final BigInteger minValue;
    public final BigInteger elementsSum;
    public final BigInteger averageValue;

    public IntegerReport(
            BigInteger elementsCount,
            BigInteger maxValue,
            BigInteger minValue,
            BigInteger elementsSum,
            BigInteger averageValue) {
        this.filteredElementsCount = elementsCount;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.elementsSum = elementsSum;
        this.averageValue = averageValue;
    }
}
