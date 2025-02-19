package org.example.file_processor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileProcessorParametersBuilder {
    private String fileNameForFloats = "floats.txt";
    private String fileNameForStrings = "strings.txt";
    private String fileNameForIntegers = "integers.txt";
    private Path path = Paths.get("");
    private boolean append = false;
    private String prefix = "";
    private boolean isStatisticsFullMode = false;
    private boolean isStatisticsOn = false;

    public void setPath(Path path) {
        path = Paths.get(path.toUri());
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public void enableStatistics() {
        isStatisticsOn = true;
    }

    public FileProcessorParameters build() {
        return new FileProcessorParameters(
                fileNameForFloats,
                fileNameForStrings,
                fileNameForIntegers,
                prefix,
                path,
                append,
                isStatisticsFullMode,
                isStatisticsOn
        );
    }

    public void setStatisticsFullMode(boolean isStatisticsFull) {
        this.isStatisticsFullMode = isStatisticsFull;
    }
}
