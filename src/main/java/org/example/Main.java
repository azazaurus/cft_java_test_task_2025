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
        ArrayList<String> fullFilesNames = new ArrayList<>();
        FileProcessorParametersBuilder parametersBuilder = new FileProcessorParametersBuilder();
        if (args.length == 0) {
            throw new RuntimeException("");
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-o")) {
                if (isValidPath(args[i + 1])) {
                    parametersBuilder.setPath(Paths.get(args[++i]));
                }
            }
            else if (args[i].equals("-p")) {
                if (isValidPrefix(args[i + 1])) {
                    parametersBuilder.setPrefix(args[++i]);
                }
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
                fullFilesNames.add(args[i]);
            }
        }

        FileProcessorParameters parameters = parametersBuilder.build();
        try (FileProcessor fileProcessor = new FileProcessor(parameters)) {
            for (int i = 0; i < fullFilesNames.size(); i++) {
                fileProcessor.processFile(fullFilesNames.get(i));
            }

            if (parameters.isStatisticsOn) {
                Report report = fileProcessor.reportStatistics();
                if (parameters.isStatisticsFullMode) {
                    System.out.print(new FullReportFormatter().format(report));
                }
                else
                    System.out.print(new ShortReportFormatter().format(report));

                }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

    public static boolean isValidPrefix(String prefix) {
        return prefix.matches("^[^</*?\"\\>:|]+$");
    }
}