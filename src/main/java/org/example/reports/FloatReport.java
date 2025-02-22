package org.example.reports;

import java.math.BigDecimal;
import java.math.BigInteger;

public class FloatReport {
    public final BigInteger filteredElementsCount;
    public final BigDecimal maxValue;
    public final BigDecimal minValue;
    public final BigDecimal elementsSum;
    public final BigDecimal averageValue;

    public FloatReport(BigInteger elementsCount, BigDecimal maxValue, BigDecimal minValue, BigDecimal elementsSum, BigDecimal averageValue) {
        this.filteredElementsCount = elementsCount;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.elementsSum = elementsSum;
        this.averageValue = averageValue;
    }
}
