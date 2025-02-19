package org.example.statistic_calculators.output_type_calculator;

import org.example.statistic_calculators.StatisticsBaseTypeCalculator;
import org.example.reports.FloatReport;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatStatisticsCalculator extends StatisticsBaseTypeCalculator {
    private BigDecimal maxValue;
    private BigDecimal minValue;
    private BigDecimal elementsSum = BigDecimal.ZERO;
    private BigDecimal averageValue;

    @Override
    public void calculate(String line) {
        countFilteredElements();
        BigDecimal newValue = new BigDecimal(line);

        if (elementsSum.equals(BigDecimal.ZERO)) {
            minValue = newValue;
            maxValue = newValue;
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
        averageValue = elementsSum.divide(new BigDecimal(getFilteredElementsCount()), RoundingMode.HALF_UP);
    }

    public FloatReport report() {
        return new FloatReport(super.getFilteredElementsCount(), maxValue, minValue, elementsSum, averageValue);
    }
}
