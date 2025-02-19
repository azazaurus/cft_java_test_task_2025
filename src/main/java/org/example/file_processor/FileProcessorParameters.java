package org.example.file_processor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileProcessorParameters {
    public final String fileNameForFloats;
    public final String fileNameForStrings;
    public final String fileNameForIntegers;
    public final Path path;
    public final boolean append;
    public final String prefix;
    public final boolean isStatisticsFullMode;
    public final boolean isStatisticsOn;

    FileProcessorParameters(
            String fileNameForFloats,
            String fileNameForStrings,
            String fileNameForIntegers,
            String prefix,
            Path path,
            boolean append,
            boolean isStatisticsFullMode,
            boolean isStatisticsOn) {
        this.fileNameForFloats = fileNameForFloats;
        this.fileNameForStrings = fileNameForStrings;
        this.fileNameForIntegers = fileNameForIntegers;
        this.prefix = prefix;
        this.path = Paths.get(path.toUri());
        this.append = append;
        this.isStatisticsFullMode = isStatisticsFullMode;
        this.isStatisticsOn = isStatisticsOn;
    }
}
