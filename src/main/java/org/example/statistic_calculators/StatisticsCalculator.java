package org.example.statistic_calculators;

import org.example.reports.Report;
import org.example.Type;

public interface StatisticsCalculator {
    void count(String line, Type lineType);
    Report reportStatistics();
}
