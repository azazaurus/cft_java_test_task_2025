package org.example.file_processor;

import org.example.Lazy;
import org.example.Logger;
import org.example.reports.Report;
import org.example.Type;
import org.example.statistic_calculators.FullStatisticsCalculator;
import org.example.statistic_calculators.NoopStatisticsCalculator;
import org.example.statistic_calculators.ShortStatisticsCalculator;
import org.example.statistic_calculators.StatisticsCalculator;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Pattern;

public class FileProcessor implements AutoCloseable{
    private static final Pattern integersRegex = Pattern.compile("^[+-]?[0-9]+$");
    private static final Pattern floatsRegex = Pattern.compile("[+-]?(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)");

    private final FileProcessorParameters parameters;
    private final HashMap<Type, Lazy<BufferedWriter>> bufferedWritersLinks = new HashMap<>();
    private final StatisticsCalculator statisticsCalculator;

    public FileProcessor(FileProcessorParameters parameters){
        this.parameters = parameters;
        if (parameters.isStatisticsOn ) {
            statisticsCalculator = parameters.isStatisticsFullMode? new FullStatisticsCalculator() : new ShortStatisticsCalculator();
        }
        else {
            statisticsCalculator = new NoopStatisticsCalculator();
        }
        createBufferedWriterAndPutItIntoHashmapIfNotNull(Type.Float, parameters.path.resolve( parameters.prefix + parameters.fileNameForFloats).toString());
        createBufferedWriterAndPutItIntoHashmapIfNotNull(Type.Integer, parameters.path.resolve( parameters.prefix + parameters.fileNameForIntegers).toString());
        createBufferedWriterAndPutItIntoHashmapIfNotNull(Type.String, parameters.path.resolve( parameters.prefix + parameters.fileNameForStrings).toString());
    }

    public void processFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = bufferedReader.readLine()) != null ) {
                Type type = tryParse(line);
                processWriting(type, line);
                statisticsCalculator.count(line, type);
            }
        } catch (FileNotFoundException e) {
            Logger.logError("Input file not found: " + filePath);
        } catch (IOException e) {
            Logger.logError("Unable to read input file " + filePath);
        }
    }

    public Report reportStatistics() {
        return statisticsCalculator.reportStatistics();
    }

    private void processWriting(Enum<Type> stringType,
                                String string) {
        BufferedWriter bufferedWriter = bufferedWritersLinks.get(stringType).get();
        if (bufferedWriter != null) {
            writeToFile(bufferedWriter, string);
        }

    }

    private void writeToFile(BufferedWriter file, String line) {
        try {
            file.write(line);
            file.newLine();
        } catch (IOException e) {
            Logger.logError("Unable to write to output file");
        }
    }

    private boolean isFloat(String input) {
        return floatsRegex.matcher(input).matches();
    }

    private boolean isInteger(String input) {
        return integersRegex.matcher(input).matches();
    }

    private Type tryParse(String input) {
        if (isInteger(input)) {
            return Type.Integer;
        }
        else if (isFloat(input)) {
            return Type.Float;
        }
        else {
            return Type.String;
        }
    }

    @Override
    public void close() {
        for (Lazy<BufferedWriter> bufferedWriter : bufferedWritersLinks.values()) {
            try {
                bufferedWriter.get().close();
            } catch (IOException e) {
                Logger.logError("Unable to close output file");
            }
        }
    }

    private void createBufferedWriterAndPutItIntoHashmapIfNotNull(Type type, String fullFileName) {
        bufferedWritersLinks.put(type, new Lazy<>(() -> createBufferedWriter(fullFileName)));
    }

    private BufferedWriter createBufferedWriter(String fullFileName) {
        BufferedWriter tempWriter;
        try {
            tempWriter = new BufferedWriter(new FileWriter(fullFileName, parameters.append));
        } catch (IOException e) {
            Logger.logError("Unable to create or open output file " + fullFileName);
            tempWriter = null;
        }
        return tempWriter;
    }
}
