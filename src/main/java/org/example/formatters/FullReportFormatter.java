package org.example.formatters;

import org.example.reports.*;

public class FullReportFormatter implements ReportFormatter {

    private static final String OUTPUT_FOR_TYPE_WITHOUT_ELEMENTS = "no elements of that type.";

    public String format(Report report) {
        if (!(report instanceof FullReport fullReport))
            throw new IllegalArgumentException(
                "Expected an argument of FullReport type, but got " + report.getClass());

        String outputString = "STATISTICS: \n";

        outputString += "Total filtered elements count: " + fullReport.elementsCount + "\n\n";

        if (fullReport.stringReport.isPresent()) {
            StringReport stringReport = fullReport.stringReport.get();
            outputString += "Strings: \n" +
                    "   elements count: " + stringReport.filteredElementsCount + "\n" +
                    "   longest string length: " + stringReport.longestStringLength + "\n" +
                    "   shortest string length: " + stringReport.shortestStringLength + "\n\n";
        }
        else {
            outputString += "Strings: ";
            outputString += OUTPUT_FOR_TYPE_WITHOUT_ELEMENTS + "\n\n";
        }

        if (fullReport.floatReport.isPresent()) {
            FloatReport floatReport = fullReport.floatReport.get();
            outputString += "Float numbers: \n" +
                    "   elements count: " + floatReport.filteredElementsCount + "\n" +
                    "   maximum value: " + floatReport.maxValue + "\n" +
                    "   minimum value: " + floatReport.minValue + "\n" +
                    "   elements sum: " + floatReport.elementsSum + "\n" +
                    "   average value: " + floatReport.averageValue  + "\n\n";
        }
        else {
            outputString += "Floats: ";
            outputString += OUTPUT_FOR_TYPE_WITHOUT_ELEMENTS + "\n\n";
        }

        if (fullReport.integerReport.isPresent()) {
            IntegerReport integerReport = fullReport.integerReport.get();
            outputString += "Integer numbers: \n" +
                    "   elements count: " + integerReport.filteredElementsCount + "\n" +
                    "   maximum value: " + integerReport.maxValue + "\n" +
                    "   minimum value: " + integerReport.minValue + "\n" +
                    "   elements sum: " + integerReport.elementsSum + "\n" +
                    "   average value: " + integerReport.averageValue  + "\n\n";
        }
        else {
            outputString += "Integers: ";
            outputString += OUTPUT_FOR_TYPE_WITHOUT_ELEMENTS + "\n";
        }

        return outputString;
    }
}
