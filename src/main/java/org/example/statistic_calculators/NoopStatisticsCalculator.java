package org.example.statistic_calculators;

import org.example.reports.Report;
import org.example.Type;

public class NoopStatisticsCalculator implements StatisticsCalculator {
    @Override
    public void count(String line, Type lineType) {

    }

    @Override
    public Report reportStatistics() {
        return null;
    }
}
