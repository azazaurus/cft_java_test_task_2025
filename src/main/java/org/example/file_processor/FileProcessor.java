package org.example.file_processor;

import org.example.Lazy;
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
    private FileProcessorParameters parameters;
    private final Pattern integersRegex = Pattern.compile("^[+-]?[0-9]+$");
    private final Pattern floatsRegex = Pattern.compile("[+-]?(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)");
    private HashMap<Type, Lazy<BufferedWriter>> bufferedWritersLinks = new HashMap<>();
    private StatisticsCalculator statisticsCalculator;
    //TODO: Как и куда добавить инициализацию переменной ниже

    public FileProcessor(FileProcessorParameters parameters) throws IOException {
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

    //TODO: возможно стоит возвращать результат работы метода, успешно ли смог выполнится или нет
    public void processFile(String filePath) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = bufferedReader.readLine()) != null ) {
                Type type = tryParse(line);
                processWriting(type, line);
                statisticsCalculator.count(line, type);
            }
        } catch (IOException e) {

            //TODO: обработать ошибки
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
            //TODO: обработать ошибки
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
    public void close() throws Exception {
        for (Lazy<BufferedWriter> bufferedWriter : bufferedWritersLinks.values()) {
            bufferedWriter.get().close();
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
            tempWriter = null;
        }
        return tempWriter;
    }
}
