package org.example.reports;

import java.math.BigInteger;

public class StringReport {
    public final BigInteger filteredElementsCount;
    public final int longestStringLength;
    public final int shortestStringLength;

    public StringReport(BigInteger filteredElementsCount, int longestStringLength, int shortestStringLength) {
        this.filteredElementsCount = filteredElementsCount;
        this.longestStringLength = longestStringLength;
        this.shortestStringLength = shortestStringLength;
    }
}
