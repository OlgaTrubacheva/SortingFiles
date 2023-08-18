package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class InputData {

    public InputData() {

    }
    public boolean parsingInputParameters(String[] args) {

        if(args.length < 3) {
            System.out.println("Некорректное количество параметров");
            return false;
        }

        int numberArg = 0;
        int countFiles = 0;
        if (args[numberArg].equals("-d")) {
            Main.asc = false;
            numberArg++;
        } else if (args[numberArg].equals("-a")) {
            numberArg++;
        }

        if (args[numberArg].equals("-i")) {
            Main.isInteger = true;
        } else if (args[numberArg].equals("-s")) {
            Main.isInteger = false;
        } else {
            System.out.println("Не задан тип элементов файла");
            return false;
        }
        numberArg++;

        if (args[numberArg].endsWith(".txt")) {
            Main.outputFileName = Paths.get(args[numberArg]);
            if(!Main.outputFileName.isAbsolute()) {
                Main.outputFileName = Main.outputFileName.toAbsolutePath();
            }
        } else {
            System.out.println("Некорректное расширение у выходного файла " + args[numberArg]);
            return false;
        }

        numberArg++;

        if ((countFiles = args.length - numberArg) == 0) {
            System.out.println("Не указаны файлы входных данных");
            return false;
        }

        Main.inputFileNames = new Path[countFiles];

        for (int i = numberArg; i < args.length; i++) {
            if (args[i].endsWith(".txt")) {

                Path path = Paths.get(args[i]);

                if(!path.isAbsolute()) {
                    path = path.toAbsolutePath();
                }

                if(Files.exists(path)) {
                    Main.inputFileNames[i - numberArg] = path;
                } else {
                    System.out.println("Файл не найден " + path.toString());
                    return false;
                }
            } else {
                System.out.println("Некорректное расширение у входного файла " + args[i]);
                return false;
            }
        }
        return true;
    }
}
