package org.example.formatters;

import org.example.reports.Report;
import org.example.reports.ShortReport;

public class ShortReportFormatter implements ReportFormatter {
    @Override
    public String format(Report report) {
        if (!(report instanceof ShortReport shortReport))
            throw new IllegalArgumentException(
                    "Expected an argument of ShortReport type, but got " + report.getClass());

        String outputString = "STATISTICS: \n";

        outputString += "Total filtered elements count: " + shortReport.elementsCount + "\n\n";

        outputString += "Strings count: " + shortReport.stringsCount + "\n";

        outputString += "Floats count: " + shortReport.floatsCount + "\n";

        outputString += "Integers count: " + shortReport.integersCount + "\n";

        return outputString;
    }
}
