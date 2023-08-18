package org.example;
import java.nio.file.Path;

public class Main {
    protected static boolean asc = true;
    protected static boolean isInteger = true;
    protected static Path[] inputFileNames;
    protected static Path outputFileName;

    public Main() {
    }

    public static void main(String[] args) {
        if ((new InputData()).parsingInputParameters(args)) {
            (new Sorting(asc, isInteger, inputFileNames, outputFileName)).sort();
        }
    }
}

