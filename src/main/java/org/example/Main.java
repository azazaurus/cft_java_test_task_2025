package org.example;

import org.example.file_processor.FileProcessor;
import org.example.file_processor.FileProcessorParameters;
import org.example.file_processor.FileProcessorParametersBuilder;
import org.example.formatters.FullReportFormatter;
import org.example.formatters.ShortReportFormatter;
import org.example.reports.FullReport;
import org.example.reports.Report;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        ArrayList<String> inputFilesNames = new ArrayList<>();
        FileProcessorParametersBuilder parametersBuilder = new FileProcessorParametersBuilder();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-o")) {
                String path = args[i + 1];
                if (isValidPath(path)) {
                    parametersBuilder.setPath(Paths.get(args[++i]));
                }
                else {
                    Logger.logError("Invalid output path: " + path);
                }
            }
            else if (args[i].equals("-p")) {
                parametersBuilder.setPrefix(args[++i]);
            }
            else if (args[i].equals("-a")) {
                parametersBuilder.setAppend(true);
            }
            else if (args[i].equals("-s")) {
                parametersBuilder.enableStatistics();
            }
            else if (args[i].equals("-f")) {
                parametersBuilder.enableStatistics();
                parametersBuilder.setStatisticsFullMode(true);
            }
            else {
                inputFilesNames.add(args[i]);
            }
        }

        FileProcessorParameters parameters = parametersBuilder.build();
        try (FileProcessor fileProcessor = new FileProcessor(parameters)) {
            for (String inputFilesName : inputFilesNames) {
                fileProcessor.processFile(inputFilesName);
            }

            if (parameters.isStatisticsOn) {
                Report report = fileProcessor.reportStatistics();
                if (parameters.isStatisticsFullMode) {
                    System.out.print(new FullReportFormatter().format(report));
                }
                else
                    System.out.print(new ShortReportFormatter().format(report));

                }
        }
    }

    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException e) {
            return false;
        }
        return true;
    }
}