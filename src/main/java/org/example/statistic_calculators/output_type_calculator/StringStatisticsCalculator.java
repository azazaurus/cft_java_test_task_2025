package org.example.statistic_calculators.output_type_calculator;

import org.example.statistic_calculators.StatisticsBaseTypeCalculator;
import org.example.reports.StringReport;

public class StringStatisticsCalculator extends StatisticsBaseTypeCalculator {
    private int longestStringLength = Integer.MIN_VALUE;
    private int shortestStringLength = Integer.MAX_VALUE;

    @Override
    public void calculate(String line) {
        countFilteredElements();
        if (line.length() > longestStringLength)
            longestStringLength = line.length();
        if (line.length() < shortestStringLength)
            shortestStringLength = line.length();
    }

    @Override
    public void finalizeCalculation() {
    }

    public StringReport report() {
        return new StringReport(super.getFilteredElementsCount(), longestStringLength, shortestStringLength);
    }
}
