package org.example;

import java.io.IOException;

import org.example.File.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Sorting {
    private boolean asc;
    private boolean isInteger;
    private Path[] inputFileNames;
    private Path outputFileName;
    private int countFiles;
    private static FileToSort[] filesToSort;
    private ArrayList<Path> tempFiles = new ArrayList<Path>();

    public Sorting(boolean asc, boolean isInteger, Path[] inputFileNames, Path outputFileName) {
        this.asc = asc;
        this.isInteger = isInteger;
        this.inputFileNames = inputFileNames;
        this.outputFileName = outputFileName;
        this.countFiles = this.inputFileNames.length;
    }

    public void sort() {

        if(countFiles == 1) {
            try {
                Path outputPath = Files.copy(inputFileNames[0], outputFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        while(countFiles > 1) {
            Path extraFile = null;

            if(countFiles%2 != 0) {
                extraFile = inputFileNames[countFiles - 1];
                countFiles = countFiles - 1;
            }

            prepareInputStreams();

            int newCountFiles = updateInputFileNames(extraFile);

            for(int i = 0; i < countFiles; i = i + 2) {

                OutputFile outputFile = new OutputFile(inputFileNames[i/2]);
                if (!createOutputStream(outputFile, i)) {
                    continue;
                }

                if (createInputStreams(i)) {
                    sortedFiles(i, outputFile);
                    closeStreams(i, outputFile);
                    deleteTempFiles(i);
                } else {
                    outputFile.closeStream();
                }
            }
            countFiles = newCountFiles;
        }

    }

    private void prepareInputStreams() {

        if (isInteger) {
            filesToSort = new FileToSortInteger[countFiles];
            for (int i = 0; i < countFiles; i++) {
                filesToSort[i] = new FileToSortInteger(inputFileNames[i], asc);
            }
        } else {
            filesToSort = new FileToSortString[countFiles];
            for (int i = 0; i < countFiles; i++) {
                filesToSort[i] = new FileToSortString(inputFileNames[i], asc);
            }
        }
    }

    private boolean createInputStreams(int i) {
        try {
            filesToSort[i].createStream();
            while(filesToSort[i].getIsNotEmpty()&&!filesToSort[i].readCurrentValue()) {}
            filesToSort[i+1].createStream();
            while(filesToSort[i+1].getIsNotEmpty()&&!filesToSort[i+1].readCurrentValue()) {}
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Файлы " + filesToSort[i].getFile().toString() + ", " + filesToSort[i+1].getFile().toString() + " не обработаны");
            return false;
        }
    }
    private boolean createOutputStream(OutputFile outputFile, int i) {
        try {
            outputFile.createStream();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не удалось открыть промежуточный файл");
            System.out.println("Файлы " + filesToSort[i].getFile().toString() + ", " + filesToSort[i+1].getFile().toString() + " не обработаны");
            return false;
        }
        return true;
    }

    private void closeStreams(int i,OutputFile outputFile){
        filesToSort[i].closeStream();
        filesToSort[i+1].closeStream();
        outputFile.closeStream();
    }
    private int updateInputFileNames(Path extraFile) {

        int newCountFiles = 0;

        if(countFiles == 2 && extraFile == null) {
            inputFileNames[0] = outputFileName;
            inputFileNames[1] = null;
            newCountFiles = 1;
            return newCountFiles;
        }

        int i;
        for(i = 0; i < countFiles/2; i++) {
            try {
                inputFileNames[i] = Files.createTempFile("sort" + i, ".txt");
                tempFiles.add(inputFileNames[i]);
            } catch (IOException e) {
                System.out.println("Не удалось создать промежуточный файл");
                e.printStackTrace();
                return 0;
            }
        }
        if (extraFile != null) {
            inputFileNames[i] = extraFile;
            inputFileNames[countFiles] = null;
            newCountFiles = i + 1;
        } else {
            newCountFiles = i;
        }
        for(i = newCountFiles; i < countFiles; i++) {
            inputFileNames[i] = null;
        }
        return newCountFiles;
    }
    private void deleteTempFiles(int i) {
        try {
            int index = tempFiles.indexOf(filesToSort[i].getFile());
            if (index >= 0 ) {
                Files.delete(tempFiles.get(index));
                tempFiles.remove(index);
            }
            index = tempFiles.indexOf(filesToSort[i+1].getFile());
            if (index >= 0 ) {
                Files.delete(tempFiles.get(index));
                tempFiles.remove(index);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sortedFiles(int i, OutputFile outputFile) {

        while(filesToSort[i].getIsNotEmpty() && filesToSort[i+1].getIsNotEmpty()) {
            if(filesToSort[i].compareTo(filesToSort[i+1])==1){
                outputFile.writeValue(filesToSort[i].getCurrentValue());
                while(filesToSort[i].getIsNotEmpty()&&!filesToSort[i].readCurrentValue()) {}
            } else {
                outputFile.writeValue(filesToSort[i+1].getCurrentValue());
                while(filesToSort[i+1].getIsNotEmpty()&&!filesToSort[i+1].readCurrentValue()) {}
            }
        }

        while(filesToSort[i].getIsNotEmpty()) {
            outputFile.writeValue(filesToSort[i].getCurrentValue());
            while(filesToSort[i].getIsNotEmpty()&&!filesToSort[i].readCurrentValue()) {}
        }

        while(filesToSort[i+1].getIsNotEmpty()) {
            outputFile.writeValue(filesToSort[i+1].getCurrentValue());
            while(filesToSort[i+1].getIsNotEmpty()&&!filesToSort[i+1].readCurrentValue()) {}
        }
    }
}

