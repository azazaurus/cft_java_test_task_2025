package org.example.reports;

import java.math.BigInteger;
import java.util.Optional;

public class FullReport implements Report {
    public final BigInteger elementsCount;
    public final Optional<StringReport> stringReport;
    public final Optional<IntegerReport> integerReport;
    public final Optional<FloatReport> floatReport;

    public FullReport(
            BigInteger elementsCount,
            Optional<StringReport> stringReport,
            Optional<IntegerReport> integerReport,
            Optional<FloatReport> floatReport) {
        this.elementsCount = elementsCount;
        this.stringReport = stringReport;
        this.integerReport = integerReport;
        this.floatReport = floatReport;
    }
}
