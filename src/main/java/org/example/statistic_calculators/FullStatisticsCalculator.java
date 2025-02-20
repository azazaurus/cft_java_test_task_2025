package org.example.statistic_calculators;

import org.example.*;
import org.example.reports.*;
import org.example.statistic_calculators.output_type_calculator.FloatStatisticsCalculator;
import org.example.statistic_calculators.output_type_calculator.IntegerStatisticsCalculator;
import org.example.statistic_calculators.output_type_calculator.StringStatisticsCalculator;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Optional;

public class FullStatisticsCalculator implements StatisticsCalculator {
    private final BigIntegerCounter allTypesFilteredElementsCount = new BigIntegerCounter();
    HashMap <Type, Lazy<StatisticsBaseTypeCalculator>> calculators = new HashMap<>();

    public FullStatisticsCalculator() {
        calculators.put(Type.String, new Lazy<>(StringStatisticsCalculator::new));
        calculators.put(Type.Integer, new Lazy<>(IntegerStatisticsCalculator::new));
        calculators.put(Type.Float, new Lazy<>(FloatStatisticsCalculator::new));
    }

    @Override
    public void count(String line, Type lineType) {
        allTypesFilteredElementsCount.count();
        StatisticsBaseTypeCalculator calculator = calculators.get(lineType).get();
        calculator.calculate(line);
    }

    private void finalizeCalculations() {
        for (Lazy<StatisticsBaseTypeCalculator> calculator: calculators.values())
            if (calculator.isInitialized()) {
                calculator.get().finalizeCalculation();
            }
    }

    @Override
    public Report reportStatistics() {
        finalizeCalculations();

        StringStatisticsCalculator stringCalculator = (StringStatisticsCalculator) calculators.get(Type.String).get();
        Optional<StringReport> stringReport = stringCalculator.getFilteredElementsCount().equals(BigInteger.ZERO) ? Optional.empty() : Optional.of(stringCalculator.report());

        IntegerStatisticsCalculator integerCalculator = (IntegerStatisticsCalculator) calculators.get(Type.Integer).get();
        Optional<IntegerReport> integerReport = integerCalculator.getFilteredElementsCount().equals(BigInteger.ZERO) ? Optional.empty() : Optional.of(integerCalculator.report());

        FloatStatisticsCalculator floatCalculator = (FloatStatisticsCalculator) calculators.get(Type.Float).get();
        Optional<FloatReport> floatReport = floatCalculator.getFilteredElementsCount().equals(BigInteger.ZERO) ? Optional.empty() : Optional.of(floatCalculator.report());

        return new FullReport(allTypesFilteredElementsCount.getCount(), stringReport, integerReport, floatReport);
    }
}
