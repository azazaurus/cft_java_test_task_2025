package org.example.statistic_calculators.output_type_calculator;

import org.example.statistic_calculators.StatisticsBaseTypeCalculator;
import org.example.reports.IntegerReport;

import java.math.BigInteger;

public class IntegerStatisticsCalculator extends StatisticsBaseTypeCalculator {
    private BigInteger maxValue;
    private BigInteger minValue;
    private BigInteger elementsSum = BigInteger.ZERO;
    private BigInteger averageValue;

    @Override
    public void calculate(String line) {
        countFilteredElements();

        BigInteger newValue = new BigInteger(line);

        if (elementsSum.equals(BigInteger.ZERO)) {
            maxValue = newValue;
            minValue = newValue;
        }
        else {
            if (newValue.compareTo(maxValue) > 0)
                maxValue = newValue;
            if (newValue.compareTo(minValue) < 0)
                minValue = newValue;
        }

        elementsSum = elementsSum.add(newValue);
    }

    @Override
    public void finalizeCalculation() {
        averageValue = elementsSum.divide(getFilteredElementsCount());
    }

    public IntegerReport report() {
        return new IntegerReport(super.getFilteredElementsCount(), maxValue, minValue, elementsSum, averageValue);
    }
}
