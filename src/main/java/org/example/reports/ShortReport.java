package org.example.reports;

import java.math.BigInteger;

public class ShortReport implements Report {
    public final BigInteger elementsCount;
    public final BigInteger stringsCount;
    public final BigInteger integersCount;
    public final BigInteger floatsCount;

    public ShortReport(
            BigInteger elementsCount,
            BigInteger stringReport,
            BigInteger integerReport,
            BigInteger floatReport) {
        this.elementsCount = elementsCount;
        this.stringsCount = stringReport;
        this.integersCount = integerReport;
        this.floatsCount = floatReport;
    }
}
