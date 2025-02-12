package org.example;

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
            else {
                fullFilesNames.add(args[i]);
            }
        }

        FileProcessorParameters parameters = parametersBuilder.build();
        try (FileProcessor fileProcessor = new FileProcessor(parameters);) {
            for (int i = 0; i < fullFilesNames.size(); i++) {
                fileProcessor.processFile(fullFilesNames.get(i));
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