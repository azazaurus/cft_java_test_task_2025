package org.example.statistic_calculators;

import org.example.*;
import org.example.reports.Report;
import org.example.reports.ShortReport;

import java.util.HashMap;

public class ShortStatisticsCalculator implements StatisticsCalculator {
    private final BigIntegerCounter allTypesFilteredElementsCount = new BigIntegerCounter();
    HashMap <Type, BigIntegerCounter> counters = new HashMap<>();

    public ShortStatisticsCalculator() {
        counters.put(Type.String, new BigIntegerCounter());
        counters.put(Type.Integer, new BigIntegerCounter());
        counters.put(Type.Float, new BigIntegerCounter());
    }

    @Override
    public void count(String line, Type lineType) {
        allTypesFilteredElementsCount.count();
        BigIntegerCounter counter = counters.get(lineType);
        counter.count();
    }

    public Report reportStatistics() {

        return new ShortReport(allTypesFilteredElementsCount.getCount(),
                counters.get(Type.String).getCount(),
                counters.get(Type.Integer).getCount(),
                counters.get(Type.Float).getCount());
    }
}
